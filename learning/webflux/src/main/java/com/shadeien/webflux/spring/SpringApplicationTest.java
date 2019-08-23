package com.shadeien.webflux.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringApplicationTest {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringApplicationTest.class);
        context.registerShutdownHook();
    }
}
