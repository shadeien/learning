package com.shadeien.concurrent.thread.control;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

@Slf4j
public class PhaserExample {
    public static void main(String[] args) {
        int times = 3;
        Phaser phaser = new Phaser(times);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < times; i++) {
            executorService.execute(() -> {
                log.info("start phase 1");
                int i1 = phaser.arriveAndAwaitAdvance();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("end phase 1:{}", i1);
                log.info("start phase 2");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int i2 = phaser.arriveAndAwaitAdvance();
                log.info("end phase 2:{}", i2);
            });
        }

        executorService.shutdown();
    }
}
