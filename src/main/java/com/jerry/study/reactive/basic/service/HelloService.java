package com.jerry.study.reactive.basic.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
//import org.springframework.util.concurrent.ListenableFuture;

@Service
@Slf4j
public class HelloService {

    //        @Async //AOP 동작 구조
//        public Future<String> hello() throws InterruptedException {
//            Thread.sleep(1000);
//            log.debug("hello()");
//            return new AsyncResult<>("hello");
//        }
//    @Async("threadPoolTaskExecutor")
//    public ListenableFuture<String> hello() throws InterruptedException {
//        Thread.sleep(1000);
//        log.debug("hello()");
//        return new AsyncResult<>("hello");
//    }

}
