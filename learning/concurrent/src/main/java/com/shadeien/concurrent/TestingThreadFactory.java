package com.shadeien.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class TestingThreadFactory implements ThreadFactory {
    public final AtomicInteger num = new AtomicInteger(0);
    private final ThreadFactory factory = Executors.defaultThreadFactory();

    @Override
    public Thread newThread(Runnable r) {
        int now = num.incrementAndGet();
        log.info("now:{}", now);
        return factory.newThread(r);
    }

    public static void main(String[] args) {
        int MAX_SIZE = 10;
        ThreadFactory factory = new TestingThreadFactory();
        ExecutorService exec = Executors.newFixedThreadPool(MAX_SIZE, factory);
        for (int i = 0; i < 10 * MAX_SIZE; i++) {
            int finalI = i;
            exec.submit(() -> {
                while (true) {
                    log.info("{}", finalI);
                    Thread.sleep(1000);
                }
            });
        }
        for (int i = 0; i < 20 && ((TestingThreadFactory) factory).num.get()<MAX_SIZE; i++) {
            try {
                log.info("i:{}", i);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("size:{}, getQueue:{}, getActiveCount:{}, getTaskCount:{}, getPoolSize:{}, coreSize:{}", ((TestingThreadFactory) factory).num.get(),
                ((ThreadPoolExecutor)exec).getQueue().size(),
                ((ThreadPoolExecutor)exec).getActiveCount(),
                ((ThreadPoolExecutor)exec).getTaskCount(),
                ((ThreadPoolExecutor)exec).getPoolSize(),
                ((ThreadPoolExecutor)exec).getCorePoolSize());

        exec.shutdownNow();
    }
}
