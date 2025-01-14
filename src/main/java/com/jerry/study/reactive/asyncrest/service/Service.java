package com.jerry.study.reactive.asyncrest.service;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

@org.springframework.stereotype.Service
public class Service {

    @Async
    public CompletableFuture<String> work(String req) {
        return CompletableFuture.completedFuture(req + "/asyncwork");
    }
}
