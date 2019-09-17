package com.shadeien.concurrent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeadLockExample {
    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();

        new Thread(() -> {
            synchronized (a) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {
                    log.info("b");
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (b) {
                synchronized (a) {
                    log.info("a");
                }
            }
        }).start();
        log.info("done");
    }
}
