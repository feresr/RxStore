package com.feresr.rxstore;

import com.feresr.rxstore.common.BasePresenter;
import com.feresr.rxstore.model.JokeRequest;
import com.feresr.rxstore.model.JokeResponse;
import com.feresr.rxstore.store.JokesStore;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by feresr on 1/2/17.
 * JokesPresenter
 */
public class JokesPresenter extends BasePresenter<JokesView> implements Action1<JokeResponse> {

    private JokesStore store;
    private Subscription subscription;

    @Inject
    JokesPresenter(JokesStore jokesStore) {
        this.store = jokesStore;
    }

    @Override
    public void onStart() {
        super.onStart();
        subscription = store.register(this);
    }

    @Override
    public void onStop() {
        store.unregister(subscription);
        super.onStop();
    }

    void fetchNewJoke() {
        store.execute(new JokeRequest());
    }

    @Override
    public void call(JokeResponse jokeResponse) {
        if (jokeResponse.isSuccessful()) {
            view.displayJoke(jokeResponse.getJoke().getValue().getJoke());
        } else {
            view.displayError(jokeResponse.getErrorString());
        }
    }
}
