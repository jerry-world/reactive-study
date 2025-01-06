package com.jerry.study.reactive.async.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EmitterController {

    Queue<DeferredResult<String>> results = new ConcurrentLinkedQueue<>();

    //emitter
    @GetMapping("/emitter")
    public ResponseBodyEmitter emitter() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                for (int i = 1; i <= 50; i++) {
                    emitter.send("<p> Stream " + i + "</p>");
                    Thread.sleep(2000);
                }
            } catch (Exception ignoredE) {
            }
        });

        return emitter;
    }
}

