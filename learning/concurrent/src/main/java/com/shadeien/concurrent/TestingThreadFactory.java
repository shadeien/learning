package com.shadeien.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class TestingThreadFactory implements ThreadFactory {
    public final AtomicInteger poolNumber = new AtomicInteger(1);
    public final AtomicInteger threadNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final String namePrefix;

    public TestingThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = "TestingThreadFactorypool-" +
                poolNumber.getAndIncrement() +
                "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);

        return t;
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
        for (int i = 0; i < 20 && ((TestingThreadFactory) factory).threadNumber.get()<MAX_SIZE; i++) {
            try {
                log.info("i:{}", i);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("size:{}, getQueue:{}, getActiveCount:{}, getTaskCount:{}, getPoolSize:{}, coreSize:{}", ((TestingThreadFactory) factory).threadNumber.get(),
                ((ThreadPoolExecutor)exec).getQueue().size(),
                ((ThreadPoolExecutor)exec).getActiveCount(),
                ((ThreadPoolExecutor)exec).getTaskCount(),
                ((ThreadPoolExecutor)exec).getPoolSize(),
                ((ThreadPoolExecutor)exec).getCorePoolSize());

        exec.shutdown();
    }
}
