package com.jerry.study.reactive.async.executor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class LoadTest {

    static AtomicInteger counter = new AtomicInteger();

    // 100개의 요청을 동시에 받았을때 Thread가 어떻게 할당되는가
    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        ExecutorService es = Executors.newFixedThreadPool(100);

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/rest?idx={idx}";

        //parties 만큼
        CyclicBarrier cyclicBarrier = new CyclicBarrier(101);

        for (int i = 0; i < 100; i++) {
            es.submit(() -> {
                int idx = counter.addAndGet(1);
                cyclicBarrier.await();
                log.info("Thread {}", idx);

                StopWatch sw = new StopWatch();
                sw.start();
                String res = restTemplate.getForObject(url, String.class, idx);
                sw.stop();
                log.info("Elasped: {} -> {} / {}", idx, sw.getTotalTimeSeconds(), res);
                return null; // Callable Interface 를 구현한 Lambda로 추론
            });
        }

        cyclicBarrier.await();
        StopWatch main = new StopWatch();
        main.start();
        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);

        main.stop();
        log.info("Total time: {}", main.getTotalTimeSeconds());

    }
}
