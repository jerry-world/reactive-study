package com.jerry.study.reactive.asyncrest.service;

import org.springframework.scheduling.annotation.Async;

@org.springframework.stereotype.Service
public class Service {

    @Async
    public String work(String req) {
        return req + "/asyncwork";
    }
}
