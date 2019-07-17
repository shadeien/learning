package com.shadeien.hessain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("spring-config-dubbo.xml")
public class SpringbootMain {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootMain.class, args);
    }
}
