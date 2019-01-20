package com.shadeien.disconf;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
@EnableApolloConfig
public class DisconfMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DisconfMain.class);
        RedisTemplate redisTemplate = context.getBean(RedisTemplate.class);
    }
}
