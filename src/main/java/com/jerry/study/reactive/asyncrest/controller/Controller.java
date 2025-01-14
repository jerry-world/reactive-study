package com.jerry.study.reactive.asyncrest.controller;

import com.jerry.study.reactive.asyncrest.service.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

//@SuppressWarnings("deprecation")
@Slf4j
@RequiredArgsConstructor
@RestController
public class Controller {

    public static final String URL1 = "http://localhost:8080/remote?req={req}";
    public static final String URL2 = "http://localhost:8080/remote2?req={req}";
    private final Service service;

    WebClient client = WebClient.create();


    @GetMapping("/rest")
    public Mono<String> rest(int idx) {
//        String s = "hello";
//        Mono<String> m = Mono.just("hello");
//        return Mono.just("hello");

        //Mono 는 Publisher 인터페이스를 구현함. 때문에,구독(subscribe) 해야함.
//        Mono<ClientResponse> res = client.get().uri(URL1, idx).exchange();
        return client
                .get()
                .uri(URL1, idx)
                .exchange()
                .flatMap(c -> c.bodyToMono(String.class))
                .flatMap(res1 -> client.get().uri(URL2, res1).exchange())
                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class))
                .flatMap(res2 -> Mono.fromCompletionStage(service.work(res2)));
    }

    //SpringBoot 2.7.1
    //비동기 작업을 처리하기 위해서, Background에 스레드를 생성하서 동작함
//    AsyncRestTemplate art = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));
//    RestTemplate rt = new RestTemplate();

//    @GetMapping("/rest")
//    public DeferredResult<String> rest(int idx) throws ExecutionException, InterruptedException {
//        String res = restTemplate.getForObject("http://localhost:8081/remote?req={req}", String.class, "hello" + idx);
//        return res;

//        DeferredResult<String> dr = new DeferredResult<>();

    //CompletableFuture로 감싸기
//        toCF(art.getForEntity("http://localhost:8081/remote?req={req}", String.class, "h" + idx))
//                .thenCompose(s -> toCF(art.getForEntity("http://localhost:8081/remote2?req={req}", String.class, s.getBody())))
//                .thenApplyAsync(s2 -> service.work(s2.getBody()))
//                .thenAccept(s3 -> dr.setResult(s3))
//                .exceptionally(e -> {
//                    dr.setErrorResult(e.getMessage());
//                    return null;
//                });

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
//        return dr;
//    }

//    <T> CompletableFuture<T> toCF(ListenableFuture<T> lf) {
//        CompletableFuture<T> cf = new CompletableFuture<>();
//        lf.addCallback(
//                cf::complete,
//                cf::completeExceptionally
//        );
//        return cf;
//    }
}
