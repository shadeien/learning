package com.shadeien.concurrent.thread.control;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreExample {
    public static void main(String[] args) {
        int times = 10;
        Semaphore semaphore = new Semaphore(times);
        ExecutorService executorService = Executors.newCachedThreadPool();
        AtomicInteger atomicInteger = new AtomicInteger();
        executorService.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    semaphore.acquire();
                    atomicInteger.incrementAndGet();
                    System.out.println("get one");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.execute(() -> {
                while (true) {
                    if (atomicInteger.get() < 5) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("release");
                    semaphore.release();
                }
        });

        executorService.shutdown();
    }
}
