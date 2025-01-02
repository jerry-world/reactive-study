package com.jerry.study.reactive.example;

import java.util.concurrent.Flow.Subscriber;

import static java.util.concurrent.Flow.*;

public abstract class DelegateSub<T, R> implements Subscriber<T> {

    Subscriber sub;

    public DelegateSub(Subscriber<? super R> sub) {
        this.sub = sub;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        sub.onSubscribe(subscription);
    }

    public void onNext(T item) {
        sub.onNext(item);
    }

    @Override
    public void onError(Throwable throwable) {
        sub.onError(throwable);
    }

    @Override
    public void onComplete() {
        sub.onComplete();
    }
}
