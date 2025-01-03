package com.jerry.study.reactive;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@Slf4j
@RequiredArgsConstructor
@EnableAsync
@SpringBootApplication
public class ReactiveApplication {

    @RestController
    @RequiredArgsConstructor
    public static class MyController {

        @GetMapping("/callable")
        public Callable<String> async() throws InterruptedException {
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

    public static void main(String[] args) {
        SpringApplication.run(ReactiveApplication.class, args);
    }


//    @RestController
//    public static class Controller {
//        @RequestMapping("/hello")
//        public Publisher<String> hello(String name) {
//            return new Publisher<String>() {
//                @Override
//                public void subscribe(Flow.Subscriber<? super String> subscriber) {
//                    subscriber.onSubscribe(new Flow.Subscription() {
//                        @Override
//                        public void request(long n) {
//                            subscriber.onNext("Hello " + name);
//                            subscriber.onComplete();
//                        }
//
//                        @Override
//                        public void cancel() {
//
//                        }
//                    });
//                }
//            };
//        }
//    }
//    @Service
//    @Slf4j
//    public static class MyService {
//
//        //        @Async //AOP 동작 구조
////        public Future<String> hello() throws InterruptedException {
////            Thread.sleep(1000);
////            log.debug("hello()");
////            return new AsyncResult<>("hello");
////        }
////        @Async("threadPoolTaskExecutor")
//        public ListenableFuture<String> hello() throws InterruptedException {
//            Thread.sleep(1000);
//            log.debug("hello()");
//            return new AsyncResult<>("hello");
//        }
//
//    }

//    @Bean
//    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
//        ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
//        te.setCorePoolSize(10);
//        te.setMaxPoolSize(100);
//        te.setQueueCapacity(200);
//        te.setThreadNamePrefix("MyThread-");
//        te.initialize();
//        return te;
//    }

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
