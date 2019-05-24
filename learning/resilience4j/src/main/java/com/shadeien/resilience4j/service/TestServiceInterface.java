package com.shadeien.resilience4j.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@CircuitBreaker(name = "backendA")
public interface TestServiceInterface {

    String aop(String str);
}
