package com.shadeien.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ConcurrentMain {

    private static void doSomething() throws InterruptedException {
        final Lock lock = new ReentrantLock();
        lock.lock();

        Thread t1 = new Thread(() -> {
            try {
                log.info("start");
//                lock.lock();
                lock.lockInterruptibly();
            } catch (Exception e) {
//                e.printStackTrace();
            }
            log.info(Thread.currentThread().getName() + " interrupted.");
        },"child thread -1");

        t1.start();
        Thread.sleep(1000);

        t1.interrupt();
        Thread.sleep(1000000);
    }

    public static void main(String[] args) throws InterruptedException {

        doSomething();
    }

    public static void test() {
        HiddenIterator hi = new HiddenIterator();
        hi.addTenThings();

        ExecutorService service = Executors.newFixedThreadPool(3);
        MyQueue queue = new MyQueue();
        service.submit(() -> {
            for (int i=0; i<100; i++) {
                try {
                    Thread.sleep(2000);
                    queue.add(i);
                    System.out.println("add element i:"+i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        service.submit(() -> {
            for (int i=0; i<30; i++) {
                System.out.println("do for and i:"+i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Integer next = queue.getNext();
                System.out.println("getNext next:"+next);
            }
        });
        service.submit(() -> {
            try {
                while (true) {
                    System.out.println("queue size:" + queue.getSize());
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.shutdown();
    }


}
