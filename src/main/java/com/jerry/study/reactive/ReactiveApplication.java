package com.jerry.study.reactive;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@RequiredArgsConstructor
@EnableAsync
@SpringBootApplication
public class ReactiveApplication {

    @Bean
    NettyReactiveWebServerFactory nettyReactiveWebServerFactory() {
        return new NettyReactiveWebServerFactory();
    }

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
