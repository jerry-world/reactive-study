package com.jerry.study.reactive.basic.executor;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.stream.Stream;

import static java.util.concurrent.Flow.*;


public class PubSub {

    public static void main(String[] args) {

        Publisher<Integer> publisher = getPublisher(Stream.iterate(1, a -> a + 1).limit(10).toList());
        Subscriber<Integer> subscriber = logSub();

        publisher.subscribe(subscriber);
    }

    private static Subscriber<Integer> logSub(){
        return new Flow.Subscriber<>() {

            Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;

                System.out.println("onSubscribe");
                subscription.request(2);
            }

            int bufferSize = 2;

            @Override
            public void onNext(Integer item) {
                System.out.println("onNext: " + item);
                if (--bufferSize <= 0) {
                    System.out.println("------");
                    bufferSize = 2;
                    subscription.request(2);
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };
    }

    private static Publisher<Integer> getPublisher(List<Integer> iterable) {
        return subscriber -> subscriber.onSubscribe(
                new Subscription() {
                    final Iterator<Integer> iter = iterable.iterator();

                    @Override
                    public void request(long n) {
                        while (n-- > 0) {
                            if (iter.hasNext()) {
                                subscriber.onNext(iter.next());
                            } else {
                                subscriber.onComplete();
                                break;
                            }
                        }
                    }

                    @Override
                    public void cancel() {

                    }
                });
    }
}
