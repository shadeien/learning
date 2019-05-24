package com.shadeien.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Slf4j
public class HotsourceDemo {

    public static void main(String[] args) {
        Flux<String> source = Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
                .map(String::toUpperCase);

        source.subscribe(d -> System.out.println("Subscriber 1: "+d));
        source.subscribe(d -> System.out.println("Subscriber 2: "+d));

        DirectProcessor<String> hotSource = DirectProcessor.create();
        Flux<String> hotFlux = hotSource.map(String::toUpperCase);

        hotFlux.subscribe(d -> System.out.println("Subscriber 1 to Hot Source: "+d));
        hotSource.onNext("blue");
        hotSource.onNext("green");

        hotFlux.subscribe(d -> System.out.println("Subscriber 2 to Hot Source: "+d));

        hotSource.onNext("orange");
        hotSource.onNext("purple");
        hotSource.onComplete();
    }
}
