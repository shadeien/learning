package com.shadeien.disconf.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${server.port}")
    String port;

    @Value("${timeout:100}")
    String timeout;

    @Value("${security.ignored}")
    String root;

    @GetMapping("{a}")
    public Object test(@PathVariable("a") String a) {
        System.out.println(System.currentTimeMillis()+":"+timeout);
//        if (StringUtils.hasText("retry")) {
//            throw new RuntimeException("retry");
//        }
        try {

            Long time = Long.valueOf(a);
            Thread.sleep(time);
        } catch (Exception e) {

        }
        return port + ":" + a + ":"+root;
    }

}
