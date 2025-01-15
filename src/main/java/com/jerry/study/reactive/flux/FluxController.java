package com.jerry.study.reactive.flux;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class FluxController {

    @GetMapping("/event/{id}")
    Mono<Event> hello(@PathVariable long id) {
        return Mono.just(Event.of(id, "event::" + id));
    }
    @GetMapping("/event2/{id}")
    Mono<List<Event>> hello2(@PathVariable long id) {
//        return Mono.just(Event.of(id, "event::" + id));
        return Mono.just(List.of(
                Event.of(1, "event::1"),
                Event.of(2, "event::2"),
                Event.of(3, "event::3"),
                Event.of(4, "event::4"),
                Event.of(5, "event::5")
        ));
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Event> events() {
//        return Flux.just(
//                Event.of(1, "event::1"),
//                Event.of(2, "event::2"),
//                Event.of(3, "event::3"),
//                Event.of(4, "event::4"),
//                Event.of(5, "event::5")
//        );

//        return Flux.fromIterable(List.of(
//                Event.of(1, "event::1"),
//                Event.of(2, "event::2"),
//                Event.of(3, "event::3"),
//                Event.of(4, "event::4"),
//                Event.of(5, "event::5")
//        ));

//        return Flux
//                .fromStream(
//                    Stream.generate(() -> Event.of(System.currentTimeMillis(), "value"))
//                )
//                .delayElements(Duration.ofMillis(100))
//                .take(10);

//        return Flux
//                .<Event>generate(sink -> sink.next(Event.of(System.currentTimeMillis(), "value")))
//                .delayElements(Duration.ofMillis(100))
//                .take(10);

//        return Flux
//                .<Event, Long>generate(() -> 1L, (id, sink) -> {
//                   sink.next(Event.of(id, "value::"+ id));
//                    return id + 1;
//                })
//                .take(100);

        Flux<Event> f = Flux
                .<Event, Long>generate(() -> 1L, (id, sink) -> {
                    sink.next(Event.of(id, "value::" + id));
                    return id + 1;
                })
                .take(100);

        Flux<String> es = Flux.generate(sink -> sink.next("Value"));
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        return Flux.zip(es, interval).map(tu -> Event.of(tu.getT2(), tu.getT1())).take(10);

    }
}
