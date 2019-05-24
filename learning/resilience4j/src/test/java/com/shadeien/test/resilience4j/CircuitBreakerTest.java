package com.shadeien.test.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest
public class CircuitBreakerTest {

    @Test
    public void circuitBreaker() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .ringBufferSizeInHalfOpenState(10)
                .ringBufferSizeInClosedState(100)
                .waitDurationInOpenState(Duration.ofSeconds(60))
                .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        CircuitBreaker breaker = registry.circuitBreaker("cb1");
        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(breaker, () -> "abc");

        Try<String> result = Try.of(supplier).map(value -> value + " efg");

        Assert.assertTrue(result.isSuccess());
        Assert.assertEquals(result.get(), "abc efg");
    }

}
