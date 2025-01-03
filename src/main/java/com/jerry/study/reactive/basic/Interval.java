package com.jerry.study.reactive.basic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Interval {
    public static void main(String[] args) {
        Flow.Publisher<Integer> pub = sub -> {
            sub.onSubscribe(new Flow.Subscription() {

                int no = 0;
                volatile boolean cancelled = false;

                @Override
                public void request(long n) {
                    //Flux take 구현해보기
                    ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
                    exec.scheduleAtFixedRate(() -> {
                        if (cancelled) {
                            exec.shutdown();
                        }
                        sub.onNext(no++);
                    }, 0, 100, TimeUnit.MILLISECONDS);
                }

                @Override
                public void cancel() {
                    cancelled = true;
                }
            });
        };

        //오퍼레이터가 하는 역할
        // 1. 데이터 변환 조작(map, reduce같은거)
        // 2. 스케줄링
        // 3. publish 하는 것을 직접 컨트롤하는 것(take...)

        Flow.Publisher<Integer> takePub = sub -> {
            pub.subscribe(new Flow.Subscriber<Integer>() {

                int count = 0;
                Flow.Subscription subscription;

                @Override
                public void onSubscribe(Flow.Subscription subscription) {
                    sub.onSubscribe(subscription);
                    this.subscription = subscription;
                }

                @Override
                public void onNext(Integer integer) {
                    sub.onNext(integer);
                    if (++count > 9) {
                        this.subscription.cancel();
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    sub.onError(throwable);
                }

                @Override
                public void onComplete() {
                    sub.onComplete();
                }
            });
        };

        takePub.subscribe(new Flow.Subscriber<Integer>() {

            @Override
            public void onSubscribe(Flow.Subscription s) {
                log.debug("onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer item) {
                log.debug("onNext: " + item);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("onError: ", throwable);
            }

            @Override
            public void onComplete() {
                log.debug("onComplete");
            }
        });
    }
}
