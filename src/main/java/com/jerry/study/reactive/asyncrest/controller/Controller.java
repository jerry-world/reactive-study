package com.jerry.study.reactive.asyncrest.controller;

import com.jerry.study.reactive.asyncrest.service.Service;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("deprecation")
@RequiredArgsConstructor
@RestController
public class Controller {

    private final Service service;

    //SpringBoot 2.7.1
    //비동기 작업을 처리하기 위해서, Background에 스레드를 생성하서 동작함
    AsyncRestTemplate art = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));
    RestTemplate rt = new RestTemplate();

    @GetMapping("/rest")
    public DeferredResult<String> rest(int idx) throws ExecutionException, InterruptedException {
//        String res = restTemplate.getForObject("http://localhost:8081/remote?req={req}", String.class, "hello" + idx);
//        return res;

        DeferredResult<String> dr = new DeferredResult<>();

        //CompletableFuture로 감싸기
        toCF(art.getForEntity("http://localhost:8081/remote?req={req}", String.class, "h" + idx))
                .thenCompose(s -> toCF(art.getForEntity("http://localhost:8081/remote2?req={req}", String.class, s.getBody())))
                .thenApplyAsync(s2 -> service.work(s2.getBody()))
                .thenAccept(s3 -> dr.setResult(s3))
                .exceptionally(e -> {
                    dr.setErrorResult(e.getMessage());
                    return null;
                });

        //Callback Hell 탈출하기
//        Completion
//                .from(art.getForEntity("http://localhost:8081/remote?req={req}", String.class, "h" + idx))
//                .andApply(s -> art.getForEntity("http://localhost:8081/remote2?req={req}", String.class, s.getBody()))
//                .andApply(s -> service.work(s.getBody()))
//                .andError(e -> dr.setErrorResult(e.toString()))
//                .andAccept(dr::setResult); // Consumer Interface 를 구현한.


//        ListenableFuture<ResponseEntity<String>> f1 = art.getForEntity("http://localhost:8081/remote?req={req}", String.class, "hello " + idx);
//        f1.addCallback(
//                s1 -> {
//                    ListenableFuture<ResponseEntity<String>> f2 = art.getForEntity("http://localhost:8081/remote2?req={req}", String.class, s1.getBody());
//                    f2.addCallback(
//                            s2 -> {
//                                ListenableFuture<String> f3 = service.work("");
//                                f3.addCallback(
//                                        dr::setResult,
//                                        e3 -> dr.setErrorResult(e3.getMessage())
//                                );
//                            },
//                            e2 -> dr.setErrorResult(e2.getMessage())
//                    );
//
//                },
//                e1 -> dr.setErrorResult(e1.getMessage())
//        );
        return dr;
    }

    <T> CompletableFuture<T> toCF(ListenableFuture<T> lf) {
        CompletableFuture<T> cf = new CompletableFuture<>();
        lf.addCallback(
                cf::complete,
                cf::completeExceptionally
        );
        return cf;
    }
}
