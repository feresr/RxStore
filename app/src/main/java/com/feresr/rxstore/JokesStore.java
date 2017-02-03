package com.feresr.rxstore;

import android.util.Log;

import com.feresr.rxstore.common.RxStore;
import com.feresr.rxstore.model.Joke;
import com.feresr.rxstore.model.JokeRequest;
import com.feresr.rxstore.model.JokeResponse;
import com.feresr.rxstore.model.Value;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by feresr on 1/2/17.
 * JokesStore
 */
public class JokesStore extends RxStore<JokeRequest, JokeResponse> {

    private static final String TAG = JokesStore.class.getSimpleName();
    private JokesEndpoint endpoints;

    @Inject
    public JokesStore(JokesEndpoint endpoints) {
        this.endpoints = endpoints;
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

    @Override
    protected Observable<JokeResponse> buildObservable(JokeRequest event) {
        return Observable.just(event).onBackpressureDrop()
                .observeOn(Schedulers.io())
                .flatMap(new Func1<JokeRequest, Observable<JokeResponse>>() {
                    @Override
                    public Observable<JokeResponse> call(final JokeRequest jokeRequest) {
                        Log.d(TAG, "Retrieving from network... " + jokeRequest);
                        return network();

                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected JokeResponse defaultValue() {
        JokeResponse jokeResponse = new JokeResponse();
        Joke joke = new Joke();
        Value value = new Value();

        jokeResponse.setJoke(joke);
        joke.setValue(value);
        value.setJoke("Chuck Norris orders you to tap 'New joke'");

        return jokeResponse;
    }
}

