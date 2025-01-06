package com.jerry.study.reactive.asyncrest.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;

@org.springframework.stereotype.Service
public class Service {

    @Async
    public ListenableFuture<String> work(String req) {
        return new AsyncResult<>(req + "/asyncwork");
    }
}
