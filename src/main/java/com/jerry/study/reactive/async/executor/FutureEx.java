package com.jerry.study.reactive.async.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class FutureEx {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();

        Future<String> f = es.submit(() -> {
            Thread.sleep(2000);
            log.info("Async");
            return "Hello";
        });


        while(!f.isDone()) {
            log.info("Exit");// submit 동작이 동작되는 중에 병렬적으로 또다른 작업을 할 수 있어.
            log.info(f.get()); // Future.get() : Blocking
        }
    }
}
