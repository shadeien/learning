package com.shadeien.gateway.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

@RestController
@Slf4j
public class TestController {


    @Autowired
    private RouteLocator routeLocator;

    @RequestMapping("/class")
    public Object classLoader() throws MalformedURLException {
        URL url = new URL("http://192.168.6.166:8081/nexus/content/groups/cit3/com/wwwarehouse/commonindustry-common/1.0-SNAPSHOT/commonindustry-common-1.0-20190911.093700-463.jar");
        URLClassLoader myClassLoader = new URLClassLoader(new URL[]{url}, Thread.currentThread().getContextClassLoader());
        URLClassLoader myClassLoader2 = new URLClassLoader(new URL[]{url});
        log.info("myClassLoader:{}", myClassLoader.getParent());
        log.info("myClassLoader2:{}", myClassLoader2.getParent());

        return null;
    }

    @RequestMapping("/watchRoute")
    public Object watchNowRoute() throws IOException {

        return routeLocator.getRoutes();
    }

    @RequestMapping("/fallback")
    public Object fallback() throws IOException {
        log.info("fallback:{}", System.currentTimeMillis());
        return Mono.just("fallback");
    }
}
