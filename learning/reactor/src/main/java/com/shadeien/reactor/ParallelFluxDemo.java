package com.shadeien.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class ParallelFluxDemo {

    public static void main(String[] args) throws InterruptedException {
        parallel();
        Thread.sleep(10000);
    }

    public static void parallel() {
        Flux.range(1, 10)
                .parallel()
                .runOn(Schedulers.parallel())
//                .sequential()
//                .groups()
//                .concatMap(g ->g.defaultIfEmpty(-1)
//                        .startWith(g.key()))
                .subscribe(i->log.info("{}:{}", i, Thread.currentThread().getName()),
                        ex->log.error("{}", ex),
                        ()->log.info("done"));
        Hooks.onErrorDropped((e)->log.error("{}", e));
    }
}
