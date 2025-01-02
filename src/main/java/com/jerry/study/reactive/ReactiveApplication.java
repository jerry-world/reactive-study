package com.jerry.study.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Publisher;

@SpringBootApplication
public class ReactiveApplication {

    @RestController
    public static class Controller {
        @RequestMapping("/hello")
        public Publisher<String> hello(String name) {
            return new Publisher<String>() {
                @Override
                public void subscribe(Flow.Subscriber<? super String> subscriber) {
                    subscriber.onSubscribe(new Flow.Subscription() {
                        @Override
                        public void request(long n) {
                            subscriber.onNext("Hello " + name);
                            subscriber.onComplete();
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                }
            };
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ReactiveApplication.class, args);
    }

}
