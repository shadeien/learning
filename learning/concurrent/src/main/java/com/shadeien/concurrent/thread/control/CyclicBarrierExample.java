package com.shadeien.concurrent.thread.control;

import com.shadeien.concurrent.thread.MyRunnable;
import com.shadeien.concurrent.thread.RetObject;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierExample {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        int times = 10;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(times, new MyRunnable(new RetObject()));
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println("await");
        for (int i = 0; i < times; i++) {
            executorService.execute(() -> {
                try {
                    System.out.println("executorService start");
                    Thread.sleep(1000);
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("executorService done");
            });
        }
        System.out.println("await done");
        executorService.shutdown();
    }
}
