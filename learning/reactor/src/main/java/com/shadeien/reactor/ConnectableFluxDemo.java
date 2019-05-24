package com.shadeien.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

@Slf4j
public class ConnectableFluxDemo {

    public static void main(String[] args) throws InterruptedException {
        connect();
        autoConnect();
    }

    public static void connect() throws InterruptedException {
        Flux<Integer> sourceFlux = Flux.range(1, 3)
                .doOnSubscribe(s -> System.out.println("subscribed to source"));
        ConnectableFlux<Integer> co = sourceFlux.publish();

        co.subscribe(System.out::println, e -> {}, () -> {});
        Thread.sleep(500);
        co.connect();
    }

    public static void autoConnect() throws InterruptedException {
        Flux<Integer> sourceFlux1 = Flux.range(1, 3).doOnSubscribe(s -> System.out.println("subscribed to source"));
        Flux<Integer> co1 = sourceFlux1.publish().autoConnect(2);

        co1.subscribe(System.out::println, e -> {}, () -> {});
        Thread.sleep(3000);
        co1.subscribe(System.out::println, e -> {}, () -> {});
    }
}
