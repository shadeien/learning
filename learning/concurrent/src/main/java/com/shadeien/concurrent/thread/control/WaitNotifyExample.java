package com.shadeien.concurrent.thread.control;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class WaitNotifyExample {

    Object monitor = new Object();
    boolean waitSign = false;

    public void doWait() {
        synchronized (monitor) {
            while (!waitSign) {
                try {
                    log.info("wait start");
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("wait done");
            }
        }
    }

    public void doNotify() {
        synchronized (monitor) {
            waitSign = true;
            monitor.notify();
            log.info("notify");
        }
    }

    public void doNotifyAll() {
        synchronized (monitor) {
            waitSign = true;
            monitor.notifyAll();
            log.info("notifyAll");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        WaitNotifyExample waitNotifyExample = new WaitNotifyExample();
//        executorService.execute(() -> {
//            try {
//                waitNotifyExample.doWait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        executorService.execute(() -> {
//                waitNotifyExample.doNotify();
//        });
        new Thread(()->waitNotifyExample.doWait()).start();
        new Thread(()->waitNotifyExample.doWait()).start();
        new Thread(()->waitNotifyExample.doWait()).start();
//        new Thread(()->waitNotifyExample.doNotify()).start();
        Thread.sleep(1000);
//        waitNotifyExample.doNotifyAll();
        waitNotifyExample.doNotify();

        while (true) {
            Thread.sleep(3000);
            log.info("main continues");
        }

//        executorService.shutdown();
    }
}
