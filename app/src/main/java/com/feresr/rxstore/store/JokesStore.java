package com.feresr.rxstore.store;

import android.util.Log;

import com.feresr.rxstore.JokesEndpoint;
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

    @Override
    protected Observable<JokeResponse> buildObservable(JokeRequest event) {
        return endpoints.getRandomJoke()
                .subscribeOn(Schedulers.io())
                .delay(2, TimeUnit.SECONDS) // simulate network delay to test screen rotation
                .map(new Func1<Response<Joke>, JokeResponse>() {
                    @Override
                    public JokeResponse call(Response<Joke> response) {
                        JokeResponse jokeResponse = new JokeResponse();
                        if (response.isSuccessful()) {
                            Log.d(TAG, "Network request executed successfully");
                            jokeResponse.setJoke(response.body());
                        } else {
                            Log.d(TAG, "Network error");
                            jokeResponse.setError("Network error: " + response.message() + " " + response.code());
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
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected JokeResponse defaultValue() {
        JokeResponse jokeResponse = new JokeResponse();
        Joke joke = new Joke();
        Value value = new Value();

        jokeResponse.setJoke(joke);
        joke.setValue(value);
        value.setJoke("Tap 'New joke'");

        return jokeResponse;
    }
}

