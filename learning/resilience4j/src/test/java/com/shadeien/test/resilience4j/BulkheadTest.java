package com.shadeien.test.resilience4j;

import com.shadeien.resilience4j.service.TestService;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class BulkheadTest {

    @Test
    public void bulkhead() {
        BulkheadConfig config = BulkheadConfig.custom().maxConcurrentCalls(1).build();
        Bulkhead bulkhead = BulkheadRegistry.of(config).bulkhead("test1");
        CheckedFunction0<String> decoratedSupplier = Bulkhead.decorateCheckedSupplier(bulkhead, () -> "This can be any method which returns: 'Hello");
        Try<String> result = Try.of(decoratedSupplier)
                .map(value -> value + " world'");

        Assert.assertTrue(result.isSuccess());
        Assert.assertEquals(result.get(), "This can be any method which returns: 'Hello world'");
        Assert.assertEquals(bulkhead.getMetrics().getAvailableConcurrentCalls(), 1);
    }

    @Test
    public void bulkhead2() {
        TestService service = Mockito.mock(TestService.class);

        BulkheadConfig config = BulkheadConfig.custom().maxConcurrentCalls(1).maxWaitTime(30000)
                .build();
        Bulkhead bulkhead = BulkheadRegistry.of(config).bulkhead("test1");
        Function<String, String> decorated = Bulkhead.decorateFunction(bulkhead, service::aop);

        CountDownLatch latch = new CountDownLatch(1);
        Mockito.when(service.aop(anyString())).thenAnswer(invocation -> {
            System.out.println(System.currentTimeMillis()+"runing");
            latch.countDown();
            Thread.currentThread().join();
            return null;
        });

        ForkJoinTask<?> task = ForkJoinPool.commonPool().submit(() -> {
            try {
                System.out.println(System.currentTimeMillis());
                decorated.apply("hello");
            } finally {
                bulkhead.onComplete();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis());
        String res = decorated.apply("1");
        Assert.assertFalse(bulkhead.isCallPermitted());
    }
}
