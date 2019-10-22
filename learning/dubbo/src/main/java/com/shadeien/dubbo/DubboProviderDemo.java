package com.shadeien.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class DubboProviderDemo {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-config-dubbo.xml");
        classPathXmlApplicationContext.start();
        System.in.read();
//        SpringApplication.run(DubboProviderDemo.class,args);
//        ConfigurableApplicationContext context = new SpringApplicationBuilder(DubboProviderDemo.class).run(args);
//        context.registerShutdownHook();
    }
}
