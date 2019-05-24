package com.shadeien.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j
public class GroupFluxDemo {

    public static void main(String[] args) {
        Flux<String> f = Flux.just(1, 3, 5, 7, 9,2)
                .groupBy(i -> i % 2 == 0 ? "even" : "odd")
                .concatMap(g -> {
                    return g.defaultIfEmpty(1)
                            .map(String::valueOf)
                            .startWith(g.key());
                        }
                );

        f.subscribe(System.out::println);

        StepVerifier.create(f)
                .expectNext("odd", "1", "3", "5", "7", "9")
                .expectNext("even", "2")
                .verifyComplete();

    }
}
