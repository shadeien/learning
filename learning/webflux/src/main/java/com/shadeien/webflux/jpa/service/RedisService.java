package com.shadeien.webflux.jpa.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class RedisService {

    @Autowired
    ReactiveRedisTemplate redisTemplate;

//    AsyncRabbitTemplate

    public Mono<Boolean> test() {
        log.info("redis test");
         redisTemplate.opsForValue().set("a", "b").subscribe(s->log.info("{}", s));

         return Mono.just(Boolean.TRUE);
    }
}
