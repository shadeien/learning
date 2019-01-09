package com.shadeien.dubbo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DubboConsumerDemo {

    public static void main(String[] args) {
//        SpringApplication.run(DubboConsumerDemo.class,args);
        ConfigurableApplicationContext context = new SpringApplicationBuilder(DubboConsumerDemo.class).run(args);
        context.registerShutdownHook();
    }
}
