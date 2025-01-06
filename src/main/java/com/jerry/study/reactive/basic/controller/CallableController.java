package com.jerry.study.reactive.basic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@Slf4j
@RestController
public class CallableController {
    @GetMapping("/callable")
    public Callable<String> callable() throws InterruptedException {
        log.info("callable");

        return () -> {
            log.info("async");
            Thread.sleep(2000);
            return "Hello";
        };
//        public String async() throws InterruptedException {
//            log.info("async");
//            Thread.sleep(2000);
//            return "hello";
    }
}
