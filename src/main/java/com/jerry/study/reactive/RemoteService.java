package com.jerry.study.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RemoteService {

    @Bean
    TomcatReactiveWebServerFactory tomcatReactiveWebServerFactory() {
        return new TomcatReactiveWebServerFactory();
    }

    public static void main(String[] args) {
        System.setProperty("server.port", "8081");
        System.setProperty("server.tomcat.threads.max", "1000");
        SpringApplication.run(RemoteService.class, args);
    }
}
