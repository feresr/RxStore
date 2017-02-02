package com.feresr.rxstore;

import android.util.Log;

import com.feresr.rxstore.common.BasePresenter;
import com.feresr.rxstore.model.JokeRequest;
import com.feresr.rxstore.model.JokeResponse;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by feresr on 1/2/17.
 * JokesPresenter
 */
public class JokesPresenter extends BasePresenter<JokesView> {

    private final static String TAG = JokesPresenter.class.getSimpleName();
    private JokesStore store;
    private Subscription subscription;

    @Inject
    JokesPresenter(JokesStore jokesStore) {
        this.store = jokesStore;
    }

    @Override
    public void onStart() {
        super.onStart();
        subscription = store.register(new JokeSubscriber());
    }

    @Override
    public void onStop() {
        store.unregister(subscription);
        super.onStop();
    }

    void fetchNewJoke() {
        store.onNext(new JokeRequest());
    }

    private class JokeSubscriber extends Subscriber<JokeResponse> {
        @Override
        public void onCompleted() {
            //should never happen!
            Log.e(TAG, "completed()");
        }

        @Override
        public void onError(Throwable e) {
            //should never happen!
            Log.e(TAG, "error()", e);
        }

        @Override
        public void onNext(JokeResponse jokeResponse) {
            if (jokeResponse.isSuccessful()) {
                view.displayJoke(jokeResponse.getJoke().getValue().getJoke());
            } else {
                view.displayError(jokeResponse.getErrorString());
            }
        }
    }
}
