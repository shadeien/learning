package com.shadeien.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class RedisController {

    @Autowired
    ReactiveRedisTemplate redisOperations;

    @GetMapping("getTest")
    public Mono<String> test() {
        String key = "test1";
        redisOperations.opsForValue().set(key, "getTest")
                .subscribe(s -> log.info("res:{}",s));
        log.info("key:{}", key);
        return redisOperations.opsForValue().get(key);
    }
}
