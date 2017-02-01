package com.feresr.rxstore;

import com.feresr.rxstore.common.BasePresenter;
import com.feresr.rxstore.model.JokeRequest;
import com.feresr.rxstore.model.JokeResponse;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by feresr on 1/2/17.
 */

public class JokesPresenter extends BasePresenter<JokesView> {

    private JokesStore store;
    private Subscription subscription;

    @Inject
    public JokesPresenter(JokesStore jokesStore) {
        this.store = jokesStore;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        subscription = store.register(new JokeSubscriber());
        store.onNext(new JokeRequest(false));

    }

    @Override
    public void onStop() {
        store.unregister(subscription);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void fetchNewJoke() {
        store.onNext(new JokeRequest(true));
    }

    private class JokeSubscriber extends Subscriber<JokeResponse> {
        @Override
        public void onCompleted() {
            //should never happen!
        }

        @Override
        public void onError(Throwable e) {
            //should never happen!
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
