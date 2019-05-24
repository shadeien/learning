package com.shadeien.resilience4j.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Service
//@CircuitBreaker(name = "backendA")
public class TestService implements TestServiceInterface {

    public String aop(String str) {
        if (str == null) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
        }
        return "success!!";
    }
}
