package com.jerry.study.reactive.executor;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class CompletableFutureEx {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService es = Executors.newFixedThreadPool(10);

        //method+Async() 는 전달한 Executor로부터 새로운 스레드를 할당받아서 비동기 작업을 수행함.

        CompletableFuture
                .supplyAsync(() -> {//Supplier: 연산 작업 수행 (return o)
                    log.info("supplyAsync");
//                    if(1==1) throw new RuntimeException();
                    return 1;
                }, es)
                .thenCompose(s1 -> {//Function: 전달받은 결과를 가지고 연산작업을 수행 (return o)
                    log.info("thenApply: {}", s1);
                    return CompletableFuture.completedFuture(s1 + 1);// FlatMap
                })
//                .thenApply(s2 -> {//Function: 전달받은 결과를 가지고 연산작업을 수행 (return o)
                .thenApplyAsync(s2 -> {//
                    log.info("thenApply: {}", s2);
                    return s2 * 3;
                }, es)
                .exceptionally(e -> {// 예외가 발생했 을 때 동작.
                    log.error("exceptionally: {}", e.getMessage());
                    return -10;
                })// 예외 발생시 복구(이 개념이 중요)
//                .thenAccept(s3 -> {//Consumer: 파라미터를 전달받고 연산작업 수행 (return x)
                .thenAcceptAsync(s3 -> {//Consumer: 파라미터를 전달받고 연산작업 수행 (return x)
                    log.info("thenAccept: {} ", s3);
                }, es);

        //--------------------------------------------------------------------------------------------------------------


        //백그라운드에서 동작하는 Thread를 생성하고 특정 작업을 수행하도록.
        // >> Runnable, Consumer, Supplier, Function, BiFunction :: 구조와 용도를 잘 이해하자.
        //CompletableFuture는 ForkJoinPool.commonPool을 통해 쓰레드 풀을 구성하고 작업ㅇ르 수행.
        //CompletionStage (since 1.8) : 하나의 비동기 작업을 수행하고, 완료되었을 때 Stage에 의존적으로 또다른 작업을 수행할 수 있도록하는 명령들을 가지고 있음.

        //컴퓨터 과학에서 동일한 사전적 의미로 다른 언어에서는 Promise, Deferred, Delay 등으로 사용됨. 미래에 대한 작업을 약속. 작업을 딜레이함. 등으로 유사한 의미로 해석
//        CompletableFuture
//                .supplyAsync(() -> {//Supplier: 연산 작업 수행 (return o)
//                    log.info("supplyAsync");
//                    return 1;
//                })
//                .thenApply(s1 -> {//Function: 전달받은 결과를 가지고 연산작업을 수행 (return o)
//                    log.info("thenApply: {}", s1);
//                    return s1 + 1;
//                })
//                .thenApply(s2 -> {//Function: 전달받은 결과를 가지고 연산작업을 수행 (return o)
//                    log.info("thenApply: {}", s2);
//                    return s2 * 3;
//                })
//                .thenAccept(s3 -> {//Consumer: 파라미터를 전달받고 연산작업 수행 (return x)
//                    log.info("thenAccept: {} ", s3);
//                });

//--------------------------------------------------------------------------------------------------------------


//        CompletableFuture
//                .runAsync(() -> log.info("runAsync"))
//                .thenRun(() -> log.info("thenRun#1"))
//                .thenRun(() -> log.info("thenRun#2"))
//                .thenRun(() -> log.info("thenRun#3"));

        log.info("exit");
        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);

//        CompletableFuture<Integer> f = CompletableFuture.completedFuture(4);

//        CompletableFuture<Integer> f = new CompletableFuture<>();
//        f.complete(4);
//        f.completeExceptionally(new RuntimeException());


//        System.out.println(f.get());

    }
}
