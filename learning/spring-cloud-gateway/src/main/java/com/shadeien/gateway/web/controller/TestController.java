package com.shadeien.gateway.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
public class TestController {


    @Autowired
    private RouteLocator routeLocator;


    @RequestMapping("/watchRoute")
    public Object watchNowRoute() throws IOException {

        return routeLocator.getRoutes();
    }

    @RequestMapping("/fallback")
    public Object fallback() throws IOException {
        System.out.println(System.currentTimeMillis());
        return Mono.just("fallback");
    }
}
