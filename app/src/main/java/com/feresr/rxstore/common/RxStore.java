package com.feresr.rxstore.common;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * Created by feresr on 26/1/17.
 * Base store class
 */
public abstract class RxStore<Input, Output> {
    private BehaviorSubject<Input> subject = BehaviorSubject.create();

    public final Subscription register(Subscriber<Output> subscriber) {
        return subject.compose(getTransformer()).subscribe(subscriber);
    }

    public final void unregister(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    protected abstract Observable.Transformer<Input, Output> getTransformer();

    public final void onNext(Input event) {
        subject.onNext(event);
    }
}
