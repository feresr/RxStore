package com.feresr.rxstore;

import android.util.Log;

import com.feresr.rxstore.common.RxStore;
import com.feresr.rxstore.model.Joke;
import com.feresr.rxstore.model.JokeRequest;
import com.feresr.rxstore.model.JokeResponse;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by feresr on 1/2/17.
 * JokesStore
 */
public class JokesStore extends RxStore<JokeRequest, JokeResponse> {

    private static final String TAG = JokesStore.class.getSimpleName();
    private JokesEndpoint endpoints;
    private JokeResponse lastResponse;

    @Inject
    public JokesStore(JokesEndpoint endpoints) {
        this.endpoints = endpoints;
    }

    @Override
    protected Observable.Transformer<JokeRequest, JokeResponse> getTransformer() {
        return new Observable.Transformer<JokeRequest, JokeResponse>() {
            @Override
            public Observable<JokeResponse> call(Observable<JokeRequest> jokeRequestObservable) {
                return jokeRequestObservable.onBackpressureDrop()
                        .observeOn(Schedulers.io())
                        .flatMap(new Func1<JokeRequest, Observable<JokeResponse>>() {
                            @Override
                            public Observable<JokeResponse> call(final JokeRequest jokeRequest) {

                                if (!jokeRequest.wasConsumed()) {
                                    Log.d(TAG, "Retrieving from network... " + jokeRequest);
                                    jokeRequest.setConsumed(true);
                                    return network();
                                } else {
                                    Log.d(TAG, "Retrieving from memory... " + jokeRequest);
                                    jokeRequest.setConsumed(true);
                                    return Observable.concat(memory(), network()).first();
                                }


                            }
                        }).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    private Observable<JokeResponse> network() {
        return endpoints.getRandomJoke()
                .delay(2, TimeUnit.SECONDS) // "simulate network delay"
                .map(new Func1<Response<Joke>, JokeResponse>() {
                    @Override
                    public JokeResponse call(Response<Joke> response) {
                        JokeResponse jokeResponse = new JokeResponse();
                        if (response.isSuccessful()) {
                            Log.d(TAG, "Network request executed successfully");
                            jokeResponse.setJoke(response.body());
                        } else {
                            Log.d(TAG, "Network error");
                            jokeResponse.setError(response.message());
                        }

                        return jokeResponse;
                    }
                })
                .doOnNext(new Action1<JokeResponse>() {
                    @Override
                    public void call(JokeResponse jokeResponse) {
                        Log.d(TAG, "Caching to memory");
                        lastResponse = jokeResponse;
                    }
                })
                .onErrorReturn(new Func1<Throwable, JokeResponse>() {
                    @Override
                    public JokeResponse call(Throwable throwable) {
                        Log.d(TAG, "A network error has been caught");
                        JokeResponse jokeResponse = new JokeResponse();
                        jokeResponse.setError(throwable.getMessage());
                        return jokeResponse;
                    }
                });
    }

    private Observable<JokeResponse> memory() {
        return Observable.just(lastResponse).takeFirst(new Func1<JokeResponse, Boolean>() {
            @Override
            public Boolean call(JokeResponse jokeResponse) {
                return jokeResponse != null;
            }
        });
    }
}

