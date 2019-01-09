package com.shadeien.concurrent;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@ToString
public class BoundedBuffer<E> {
    private final Semaphore availableItems, avilableSpaces;
    private final E[] items;
    private int putPos = 0, takePos = 0;

    public BoundedBuffer(int capacity) {
        availableItems = new Semaphore(0);
        avilableSpaces = new Semaphore(capacity);
        items = (E[]) new Object[capacity];
    }

    public void put(E e) throws InterruptedException {
        avilableSpaces.acquire();
        doInsert(e);
        availableItems.release();
    }

    private synchronized void doInsert(E e) {
        int i = putPos;
        items[i] = e;
//        log.info("put i:{}, value:{}", i, e);
        putPos = (++i == items.length)?0:i;
    }

    public E take() throws InterruptedException {
        availableItems.acquire();
        E item = doExtract();
        avilableSpaces.release();

        return item;
    }

    private synchronized E doExtract() {
        int i = takePos;
        E e = items[i];
        items[i] = null;
        takePos = (++i == items.length)?0:i;
//        log.info("take i:{}, value:{}", i, e);
        return e;
    }

    public static void main(String[] args) throws InterruptedException {
        BoundedBuffer<String> boundedBuffer = new BoundedBuffer<>(1000);
        int threadNum = 2;
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        ExecutorService exec = Executors.newFixedThreadPool(threadNum);
        exec.submit(() -> {
            try {
                for (int i=0; i<500; i++) {
                    String v = "a" + String.valueOf(i);
                    boundedBuffer.put(v);
                }
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        exec.submit(() -> {
            try {
                for (int i=0; i<500; i++) {
                    String v = "b" + String.valueOf(i);
                    boundedBuffer.put(v);
                }
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        countDownLatch.await();
        exec.shutdown();

        log.info("{}", boundedBuffer);
    }
}
