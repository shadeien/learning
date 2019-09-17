package com.shadeien.concurrent.thread.control;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CountdownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);
        ExecutorService executorService = Executors.newCachedThreadPool();
        AtomicInteger integer = new AtomicInteger();
        executorService.execute(() -> {
            boolean isOver = false;
            while (!isOver) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int i = integer.incrementAndGet();
                if (i >= 10) {
                    isOver = true;
                }
                latch.countDown();
                System.out.println("count down");
            }
        });

        latch.await();
        System.out.println("main done");
        executorService.shutdown();
    }

}
