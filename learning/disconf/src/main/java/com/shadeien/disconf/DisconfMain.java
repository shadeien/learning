package com.shadeien.disconf;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableApolloConfig
@EnableEurekaClient
public class DisconfMain {
    public static void main(String[] args) {
//        List<String> arr = Lists.newArrayList()
//        new SpringApplicationBuilder(DisconfMain.class).run(args);
//        ConfigurableApplicationContext context = SpringApplication.run(DisconfMain.class);
//        RedisTemplate redisTemplate = context.getBean(RedisTemplate.class);

//        double current = ThreadLocalRandom.current().nextInt();
        Map<String, String> map = new HashMap<>();
        String a = map.get("a");
        System.out.println(a);
    }
}
