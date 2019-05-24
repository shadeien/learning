package com.shadeien.learning.redis.publish;

import com.shadeien.learning.redis.spring.MyRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisPublisher {

    @Autowired
    MyRedisTemplate redisTemplate;

    /**
     * 发布信息给订阅的服务器
     * @param topic
     * @param data
     */
    public void publish(String topic, Object data) {
        log.info("publish channel:{}, with data:{}", topic, data);
        redisTemplate.convertAndSend(topic, data);
    }

    public void setExpired(String key, Object data, long timeout) {
        redisTemplate.opsForValue().set(key, data);
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }
}
