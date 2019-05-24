package com.shadeien.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class ReactorMain {

    public static void main(String[] args) throws InterruptedException {

        Flux<String> seq1 = Flux.just("foo", "bar", "foobar")
                .map(str -> {
                    if (str.equals("bar"))
                        throw new RuntimeException("bar");
                    return str;
                });

        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
        Flux<String> seq2 = Flux.fromIterable(iterable);

        Mono<String> seq3 = Mono.from(seq1);

        seq1.subscribe(str -> log.info("seq1:{}", str),
                ex -> log.info("{}", ex),
                () -> log.info("completeConsumer"),
                scription -> {
//                    scription.cancel();
                    scription.request(Long.MAX_VALUE);
                });
        seq1.subscribe(str -> log.info("seq3:{}", str),
                ex -> log.info("{}", ex),
                () -> log.info("completeConsumer"));
    }
}
