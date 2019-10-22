package com.shadeien.webflux.controller;

import com.shadeien.webflux.jpa.service.DeviceInfoService;
import com.shadeien.webflux.jpa.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@RestController
@Slf4j
public class WebFluxController {

    @Autowired
    DeviceInfoService deviceInfoService;

//    @Autowired
    Sender sender;

    @GetMapping("/rabbitTest")
    public Mono<String> rabbitTest() {
        Flux<OutboundMessage> outboundFlux  = Flux.range(1, 10)
                .map(i -> new OutboundMessage(
                        "reactive.exchange",
                        "a.b",
                         "hello".getBytes()
                ));

        sender.sendWithPublishConfirms(outboundFlux).subscribe(res -> log.info("res:{}", res));

        log.info("success");
        return Mono.just("success");

    }

//    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/rabbitTemplateTest")
    public void rabbitTemplateTest() {
        rabbitTemplate.convertAndSend("reactive.exchange", "a.b",
                "hello".getBytes());
    }

    @GetMapping("/test")
    public Object test() {
        log.info("saveNewDevice");
        deviceInfoService.saveNewDevice();

        return Mono.just("Hello World");
    }

    private final Map<String, ServerSentEvent<Integer>> data = new ConcurrentHashMap<>();
    private final LinkedBlockingQueue<ServerSentEvent<Integer>> queue = new LinkedBlockingQueue<>();

    @GetMapping("/randomNumbers")
    public Flux<ServerSentEvent<Integer>> randomNumbers() {
//        return Flux.fromIterable(this.data.values());
        return Flux.interval(Duration.ofSeconds(1))
//                .map(seq -> Tuples.of(seq, data))
                .map(seq -> {
                    ServerSentEvent<Integer> res = queue.poll();
                    if (null == res) {
                        res = ServerSentEvent.<Integer>builder()
//                                .event("random")
//                                .id(Long.toString(seq))
//                                .data(ThreadLocalRandom.current().nextInt())
                                .build();
                    }
                    return res;
                });
    }

    @GetMapping("/test2")
    public Object test2() {
        log.info("test2");
        deviceInfoService.find();

        return Flux.just("success").delayElements(Duration.ofSeconds(3));
    }

    @GetMapping("/test1")
    public Object test1() {
        log.info("test1");
        deviceInfoService.saveNewDevice1();
        ServerSentEvent<Integer> event = ServerSentEvent.<Integer>builder()
                .event("random")
//                        .id(Long.toString(data.getT1()))
                .data(1111111)
                .build();

        queue.add(event);

        return Flux.just("success").delayElements(Duration.ofSeconds(3));

    }

    @Autowired
    RedisService redisService;

    @GetMapping("/testredis")
    public Object testredis(ServerWebExchange serverWebExchange) {
//        Webserver
        log.info("testredis:{}", serverWebExchange.getAttributes().get("a"));
        return redisService.test();

//        return Flux.just("success").delayElements(Duration.ofSeconds(3));
    }

    @RequestMapping("/{user}")
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
