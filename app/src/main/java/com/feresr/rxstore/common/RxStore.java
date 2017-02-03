package com.feresr.rxstore.common;

import com.jakewharton.rxrelay.BehaviorRelay;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by feresr on 26/1/17.
 * Base store class
 */
public abstract class RxStore<Input, Output> {
    private BehaviorRelay<Output> relay = BehaviorRelay.create(defaultValue());

    public final Subscription register(Subscriber<Output> subscriber) {
        return relay.subscribe(subscriber);
    }

    public final void unregister(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public final void execute(Input event) {
        buildObservable(event).subscribe(relay);
    }

    /**
     * @return the first or default value emitted to subscribers
     */
    protected Output defaultValue() {
        return null;
    }

    /**
     * @return an buildObservable responsible of handling its own errors.
     */
    protected abstract Observable<Output> buildObservable(Input event);
}