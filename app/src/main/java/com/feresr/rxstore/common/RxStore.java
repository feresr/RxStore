package com.feresr.rxstore.common;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

/**
 * Created by feresr on 26/1/17.
 * Base store class
 */
public abstract class RxStore<Input, Output> {
    private BehaviorSubject<Input> subject = BehaviorSubject.create();
    private Observable<Output> observable;
    private final static String TAG = RxStore.class.getSimpleName();

    public final Subscription register(Subscriber<Output> subscriber) {
        if (observable == null) {
            observable = subject.compose(getTransformer());
        }
        Log.d(TAG, ".subscribe(s)");
        return observable.subscribe(subscriber);
    }

    public final void unregister(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            Log.d(TAG, ".unsubscribe()");
            subscription.unsubscribe();
        }
    }

    protected abstract Observable.Transformer<Input, Output> getTransformer();

    public final void onNext(Input event) {
        subject.onNext(event);
    }
}
