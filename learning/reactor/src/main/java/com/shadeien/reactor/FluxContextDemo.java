package com.shadeien.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class FluxContextDemo {

    public static void main(String[] args) throws InterruptedException {
        String key = "msg";
        Mono<String> m = Mono.just("Hello")
                .flatMap(s -> Mono.subscriberContext().map(ctx -> s + " " + ctx.get(key)))
                .subscriberContext(ctx -> ctx.put(key, "World"));

        m.subscribe(System.out::println);
        Mono.subscriberContext().map(ctx->{
                    log.info("context:{}", ctx);
                    return ctx;
                })
                .subscriberContext(ctx->ctx.put(key, "value"))
                .subscribe(System.out::println);

    }

}
