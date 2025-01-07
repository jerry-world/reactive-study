package com.jerry.study.reactive.executor;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Operator {

    // publisher --- DATA ---> subscriber
    // Transform || Operator ( using Stream..)
    // publisher --- DATA --- Operator(DATA) ---> DATA 1 ---> Operator(DATA1) ---> DATA 2 ----> subscriber
    // 1. map (data 1 -> function(data 1) -> d2)


    public static void main(String[] args) {

        Publisher<Integer> publisher = getPublisher(Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList()));
//        Publisher<Integer> mapPub = mapPub(publisher, s -> s * 10);
//        Publisher<Integer> sumPub = sumPub(publisher);
//        Publisher<Integer> reducePub = reducePub(publisher, 0, (a, b) -> a + b);
//        Publisher<String> mapPub = mapPub(publisher, s -> "[" + s + "]");
//        Publisher<String> reducePub = reducePub(publisher, "", (a, b) -> a + "-" + b);
        Publisher<StringBuilder> reducePub = reducePub(publisher, new StringBuilder(), (a, b) -> a.append(b + ","));
        reducePub.subscribe(logSub());

    }

    // [Reduce]
    // 1,2,3,4,5
    // (0,1) -> 0+1 = 1
    // (1,2) -> 1+2 = 3
    // (3,3) -> 3+3 = 6
    // ...
    private static <T, R> Publisher<R> reducePub(Publisher<T> pub, R init, BiFunction<R, T, R> bf) {
        return new Publisher<R>() {
            @Override
            public void subscribe(Subscriber<? super R> sub) {
                pub.subscribe(new DelegateSub<T, R>(sub) {

                    R result = init;

                    @Override
                    public void onNext(T item) {
                        result = (bf.apply(result, item));
                    }

                    @Override
                    public void onComplete() {
                        sub.onNext(result);
                        super.onComplete();
                    }
                });
            }


        };
    }


//    private static Publisher<Integer> sumPub(Publisher<Integer> publisher) {
//        return new Publisher<Integer>() {
//            @Override
//            public void subscribe(Subscriber<? super Integer> subscriber) {
//                publisher.subscribe(new DelegateSub(subscriber) {
//                    int sum = 0;
//
//                    @Override
//                    public void onNext(Integer item) {
//                        sum += item;
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        subscriber.onNext(sum);
//                        sub.onComplete();
//                    }
//                });
//            }
//        };
//    }

    private static <T, R> Publisher<R> mapPub(Publisher<T> pub, Function<T, R> f) {
        return new Publisher<R>() {
            @Override
            public void subscribe(Subscriber<? super R> sub) {
                pub.subscribe(new DelegateSub<T, R>(sub) {
                    @Override
                    public void onNext(T item) {
                        super.onNext((T) f.apply(item));
                    }
                });
            }
        };
    }

    private static <T> Subscriber<T> logSub() {
        return new Subscriber<T>() {

            Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;

                System.out.println("onSubscribe");
                subscription.request(Long.MAX_VALUE);
            }


            @Override
            public void onNext(T item) {
                System.out.println("onNext: " + item);
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
