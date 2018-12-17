package com.shadeien.learning.redis;

import com.shadeien.learning.redis.publish.RedisPublisher;
import com.shadeien.learning.redis.spring.MyRedisTemplate;
import com.shadeien.learning.redis.subscribe.RedisSubscriber;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@SpringBootApplication
public class PSApplication {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(PSApplication.class, args);
        RedisPublisher publisher = context.getBean(RedisPublisher.class);
        RedisSubscriber subscriber = context.getBean(RedisSubscriber.class);

        String topic = "subscribe:topic";
        subscriber.subscribe(topic);
        Thread.sleep(1000);
        publisher.publish(topic, "publish");

        context.registerShutdownHook();
        Thread.sleep(3000);
        context.close();
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        return container;
    }

    @Bean
    @ConditionalOnMissingBean
    public MyRedisTemplate myRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        MyRedisTemplate template = new MyRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);

        return template;
    }
}
