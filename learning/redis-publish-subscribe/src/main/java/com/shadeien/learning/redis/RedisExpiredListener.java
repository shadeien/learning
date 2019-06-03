package com.shadeien.learning.redis;

import com.shadeien.learning.redis.spring.MyRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class RedisExpiredListener implements MessageListener {

    @Autowired
    MyRedisTemplate redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
//        Object value = redisTemplate.opsForValue().get(new String(message.getBody()));
//        log.info("value:{}", value);
        log.info("receiveMessage:{}, pattern:{}", message, new String(pattern));
    }
}
