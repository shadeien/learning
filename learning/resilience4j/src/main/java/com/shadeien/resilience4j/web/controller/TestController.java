package com.shadeien.resilience4j.web.controller;

import com.shadeien.resilience4j.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping("/fallback/{str}")
    public Object fallback(@PathVariable("str") String str) throws Exception {
        Object res;
        if (str.equals("null")) {
            res = testService.aop(null);
        } else
            res = testService.aop(str);
        System.out.println(System.currentTimeMillis());
        return Mono.just(res);
    }
}
