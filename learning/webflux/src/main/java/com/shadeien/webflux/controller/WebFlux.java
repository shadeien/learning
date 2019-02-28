package com.shadeien.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class WebFlux {

    @GetMapping("/{user}")
    public Object getUser(@PathVariable Long user) {
        List<String> words = Arrays.asList("a", "b", "c");
        Object obj = "";
        if (user == 1) {
            Mono<String> helloWorld = Mono.just("Hello World");
            obj = helloWorld;
        } else if (user == 2) {
            Flux<String> fewWords = Flux.just("Hello", "World");
            obj = fewWords;
        } else if (user == 3) {
            CompletableFuture aCompletableFuture = CompletableFuture.supplyAsync(() -> "success");
            obj = Mono.fromFuture(aCompletableFuture);
        } else if (user == 4) {
            CompletableFuture aCompletableFuture = CompletableFuture.runAsync(() -> {

            });
            obj = Mono.fromFuture(aCompletableFuture);
        } else {
            Flux<String> manyWords = Flux.fromIterable(words);
            obj = manyWords;
        }
        return obj;
    }

    @GetMapping("/1/{user}")
    public Object getUserCustomers(@PathVariable Long user) {
        Object obj = "";
        Flux<String> result = Flux.just("aaa", "bbb", "abc", "ccc");
        Flux<String> result1 = Flux.just("aaa", "bbb", "ccc");
        if (user == 1) {
            obj = result.filter(s -> s.startsWith("a"));
        } else if (user == 2) {
            obj = result.map(s -> s.concat("_map"));
        } else if (user == 3) {
            obj = result.flatMap(s -> Flux.just(s.concat("_flatmap")));
        } else if (user == 4) {
            obj = Flux.zip(result, result1);
        } else {
            obj = result.reduce("init", (s, b) -> {
                System.out.println("s:"+s);
                System.out.println("b:"+b);

                return s;
            });
        }
        return obj;
    }
}
