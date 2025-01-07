package com.jerry.study.reactive.executor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FluxScheduler {
    public static void main(String[] args) throws InterruptedException {
//        Flux.range(1, 10)
//                .publishOn(Schedulers.newSingle("pub"))
//                .log()
////                .subscribeOn(Schedulers.newSingle("sub"))
//                .reduce((a,b)-> a+b)
//                .log()
//                .subscribe(System.out::println, System.err::println, () -> System.out.println("Done!"));
//
//        System.out.println("exit");

        Flux.interval(Duration.ofMillis(500))
                .take(10)
                .subscribe(s -> log.debug("onNext:{}", s));

        TimeUnit.SECONDS.sleep(10);
    }
}
