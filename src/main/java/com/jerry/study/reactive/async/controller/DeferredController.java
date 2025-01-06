package com.jerry.study.reactive.async.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DeferredController {

    Queue<DeferredResult<String>> results = new ConcurrentLinkedQueue<>();

    //Deferred Result
    @GetMapping("/dr")
    public DeferredResult<String> deferredResult() {
        log.info("deferredResult");

        DeferredResult<String> deferredResult = new DeferredResult<>(600000L);
        results.add(deferredResult);
        return deferredResult;
    }

    @GetMapping("/dr/count")
    public String drCount() {
        return String.valueOf(results.size());
    }

    @GetMapping("/dr/event")
    public String drEvent(String msg) {
        for (DeferredResult<String> dr : results) {
            dr.setResult("Hello " + msg);
            results.remove(dr);
        }
        return "OK";
    }

}
