package com.jerry.study.reactive.basic;

import reactor.core.publisher.Flux;

//리액터는 Publisher를 구현한 Flux를 제공
public class ReactorEx {
    public static void main(String[] args) {
        Flux.<Integer>create(e -> {
                    e.next(1);
                    e.next(2);
                    e.next(3);
                    e.next(4);
                    e.next(5);
                    e.complete();
                })
                .log()
                .map(s -> s * 10)
                .log()
                .reduce(0, (a, b) -> a + b)
                .log()
                .subscribe(System.out::println);
    }
}
