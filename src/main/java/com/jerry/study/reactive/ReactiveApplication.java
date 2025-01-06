package com.jerry.study.reactive;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

@Slf4j
@RequiredArgsConstructor
@EnableAsync
@SpringBootApplication
public class ReactiveApplication {



    public static void main(String[] args) {
        SpringApplication.run(ReactiveApplication.class, args);
    }


//    public static void main(String[] args) {
//        try (ConfigurableApplicationContext c = SpringApplication.run(ReactiveApplication.class, args)) {
//        }
//    }

//    private final MyService myService;

//    @Bean
//    ApplicationRunner run() {
//        return _ -> {
//            log.info("run()");
//            ListenableFuture<String> f = myService.hello();
//            f.addCallback(s -> System.out.println(s), e -> System.out.println(e));
//            log.info("exit : " + f.isDone());
//            log.info("result : " + f.get());
//        };
//    }
}
