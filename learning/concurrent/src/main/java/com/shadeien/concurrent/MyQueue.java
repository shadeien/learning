package com.shadeien.concurrent;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class MyQueue {
    private BlockingQueue<Integer> blockingQueue;

    public MyQueue() {
        this.blockingQueue = new ArrayBlockingQueue<>(10);
    }

    public Integer getNext() {
        try {
            System.out.println("try to poll a element for 500 milliseconds to timeout");
            return blockingQueue.poll(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Integer takeNext() {
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void add(Integer integer) throws InterruptedException {
        blockingQueue.put(integer);
    }

    public int getSize() {
        return blockingQueue.size();
    }
}
