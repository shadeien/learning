package com.shadeien.concurrent;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class HiddenIterator {
    public static void main(String[] args) {
        HiddenIterator hiddenIterator = new HiddenIterator();
        ExecutorService pool = Executors.newFixedThreadPool(2);
        AtomicBoolean isRun = new AtomicBoolean(true);
        pool.submit(() -> {
            while (true) {
                hiddenIterator.addTenThings();
            }
//            try {
//                hiddenIterator.addTenThings();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                isRun.set(false);
//            }
        });
        pool.submit(() -> {
            while (isRun.get()) {
                System.out.println(hiddenIterator.set);
            }
        });

        pool.shutdown();
    }

    final Set<Integer> set = new HashSet<>();

    public void add(Integer i){
        set.add(i);
    }

    public void remove(Integer i){
        set.remove(i);
    }

    public void addTenThings() throws InterruptedException {
        Random r = new Random();
        for (int i = 0; i<1000; i++) {
            add(r.nextInt());
            Thread.sleep(10);
        }

    }
}
