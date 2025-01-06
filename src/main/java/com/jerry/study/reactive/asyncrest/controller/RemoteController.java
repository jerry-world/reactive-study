package com.jerry.study.reactive.asyncrest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoteController {

    @GetMapping("/remote")
    public String remote(String req) throws InterruptedException {
        Thread.sleep(2000);
        return req + "/service";
    }

    @GetMapping("/remote2")
    public String remote2(String req) throws InterruptedException {
        Thread.sleep(2000);
        return req + "/service2";
    }
}

