package com.shadeien.learning.redis;

import com.shadeien.learning.redis.spring.MyRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class PSApplication {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(PSApplication.class, args);
        MyRedisTemplate redisTemplate = context.getBean(MyRedisTemplate.class);
        log.info("aaaa:{}", redisTemplate.opsForValue().get("aaa"));
//        redisTemplate.opsForHash().put("all", "topic.key..1.ios", "ios");
//        redisTemplate.opsForHash().put("all", "topic.key..1.web", "web");
//        redisTemplate.opsForHash().put("all", "topic.key.2.web", "web2");
//        Boolean all = redisTemplate.opsForHash().hasKey("all", "topic.key.1.*");
//        Boolean hasKey = redisTemplate.opsForHash().hasKey("all", "topic.key.1.ios");

        List<String> keys = new ArrayList<String>();
        keys.add("channels");
        DefaultRedisScript<List> script = new DefaultRedisScript<List>("local size = redis.call('pubsub');return true", List.class);
        List execute = redisTemplate.execute(script, keys, new Object[] {"*"});
        log.info("execute:{}", execute);

//        log.info("all:{} hasKey:{}", all, hasKey);

//        redisTemplate.opsForSet().add("all", "1");
//        redisTemplate.opsForSet().add("all", "2");
//        try {
//            Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan("all",
//                    ScanOptions.scanOptions().match("topic.key..1.*").count(1).build());
//            while (cursor.hasNext()) {
//                Map.Entry<Object, Object> next = cursor.next();
//                Object key = next.getKey();
//                Object valueSet = next.getValue();
//                log.info("key:{}, set:{}", key, valueSet);
//            }
//            //关闭scan
//            cursor.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }




//        RedisSubscriber subscriber = context.getBean(RedisSubscriber.class);
//        log.info("start");
//
////        String topic = "__keyevent@0__:expired";
//        String topic = "subscribe.topic.1";
//        subscriber.subscribe(topic);
//        Thread.sleep(1000);
//
//
//        RedisPublisher publisher = context.getBean(RedisPublisher.class);
//        Set<String> keys = redisTemplate.keys("subscribe.topic.*");
//        log.info("keys:{}", keys);
//        publisher.publish("subscribe.topic.*", "1234");
//        publisher.setExpired("abc", "publish", 5);

        context.registerShutdownHook();
        Thread.sleep(60000);
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
