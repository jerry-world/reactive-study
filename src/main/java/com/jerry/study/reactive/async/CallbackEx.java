package com.jerry.study.reactive.async;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;

//Future -> Blocking 이지만, 비동기 작업에 대한 결과를 받아낼 수 있는 핸들러
//Callback -> 예외가 발생했을때, 보다 우아하게 예외를 받아 처리할 수 있음

@Slf4j
public class CallbackEx {

    interface SuccessCallback {
        void onSuccess(String result);

    }

    interface ExceptionCallback {
        void onError(Throwable t);
    }

    public static class CallbackFutureTask extends FutureTask<String> {

        SuccessCallback sc;
        ExceptionCallback ec;

        public CallbackFutureTask(Callable<String> callable, SuccessCallback sc, ExceptionCallback ec) {
            super(callable);
            this.sc = Objects.requireNonNull(sc);
            this.ec = Objects.requireNonNull(ec);
        }

        @Override
        protected void done() {
            try {
                sc.onSuccess(get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                ec.onError(e.getCause());
            }
        }


    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();

        CallbackFutureTask cft = new CallbackFutureTask(() -> {
            Thread.sleep(2000);
            if(1==1) throw new RuntimeException("Async ERROR!!");
            log.info("Async");
            return "Hello";
        },
                s -> System.out.println("Result: " + s),
                e -> System.out.println("Error: " + e.getMessage()));

        es.execute(cft);
        System.out.println("aaa");

        //Future 와 비동기로 수행할 작업이 포함되어있음.
//        FutureTask<String> f = new FutureTask<>(() -> {
//            Thread.sleep(2000);
//            log.info("Async");
//            return "Hello";
//        }) {
//            @Override
//            protected void done() {
//                try {
//                    System.out.println(get());
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                } catch (ExecutionException e) {
//                    throw new RuntimeException(e);
//                }
//                super.done();
//            }
//        };
//
//        log.info("Main!!");
//        es.execute(f);

        es.shutdown();

    }
}
