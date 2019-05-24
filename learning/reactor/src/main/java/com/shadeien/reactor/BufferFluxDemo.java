package com.shadeien.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
public class BufferFluxDemo {

    public static void main(String[] args) {
//        buffer();
        bufferWhile();
        bufferUntil();
    }

    public static void bufferUntil() {
        Flux<List<Integer>> f = Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13)
                .bufferUntil(i -> i % 2 == 0);

        f.subscribe(System.out::println,
                ex -> {},
                ()-> System.out.println("done")
        );
    }

    public static void bufferWhile() {
        Flux<List<Integer>> f = Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13)
                .bufferWhile(i -> i % 2 == 0);

        f.subscribe(System.out::println,
                ex -> {},
                ()-> System.out.println("done")
        );
    }

    public static void buffer() {
        Flux<List<Integer>> f = Flux.range(1,10)
                .buffer(5,3);

        f.subscribe(System.out::println,
                ex -> {},
                ()-> System.out.println("done")
        );
    }

}
