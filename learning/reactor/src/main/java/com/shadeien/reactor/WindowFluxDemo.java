package com.shadeien.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class WindowFluxDemo {

    public static void main(String[] args) {
        windowWhile();
    }

    public static void windowWhile() {
        Flux<String> f = Flux.range(1,10)//Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13)
                .windowWhile(i->i%2==0)
                .concatMap(g -> g.defaultIfEmpty(-1)
                        .map((a)->String.valueOf(a))
                        .startWith(Mono.just("::"))
                );

        f.subscribe(System.out::println,
                ex -> {},
                ()-> System.out.println("done")
        );
    }

    public static void window() {
        Flux<String> f = Flux.range(1, 10)
                .window(3, 5)
                .concatMap(g -> g.defaultIfEmpty(-1)
                        .map((a)->String.valueOf(a))
                        .startWith(Mono.just("::")));

        f.subscribe(System.out::println);
    }
}
