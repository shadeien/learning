package com.shadeien.dubbo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class LogMain {
    public static void main(String[] args) {
        SpringApplication.run(LogMain.class);
        log.info("info");
        log.warn("warn");
        log.error("error");
    }
}
