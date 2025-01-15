package com.jerry.study.reactive.webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class WebFluxController {
    @GetMapping("/webflux")
    Mono<String> hello() {
        log.info("Pos#1");
        Mono<String> m = Mono.fromSupplier(this::generateHello).doOnNext(log::info).log();
//        m.subscribe();
//        m.subscribe();
//        m.subscribe();
        String block = m.block();
        log.info("Pos#2:: {}", block);
        return m;
    }

    private String generateHello(){

        log.info("method::generateHello()");
        return "Hello Mono";
    }

}
