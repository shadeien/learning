package com.shadeien.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ConditionBoundedBuffer<T> {
    protected final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final T[] items;
    private int tail, head, count;

    public ConditionBoundedBuffer(int bufferSize) {
        items = (T[]) new Object[bufferSize];
        tail = head = count = 0;
    }

    public void put(T x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                log.info("not full await");
                notFull.await();
            }
            items[tail] = x;
            if (++tail == items.length) {
                log.info("tail = 0");
                tail = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            T x = items[head];
            items[head] = null;
            if (++head == items.length) {
                log.info("head = 0");
                head = 0;
            }
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionBoundedBuffer<Integer> conditionBoundedBuffer = new ConditionBoundedBuffer<>(10);
        ExecutorService exec = Executors.newFixedThreadPool(1);
        exec.submit(()->{
            try {
                Thread.sleep(1000);
                conditionBoundedBuffer.put(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Integer value = conditionBoundedBuffer.take();
        log.info("{}", value);
        conditionBoundedBuffer.put(2);
        value = conditionBoundedBuffer.take();
        log.info("{}", value);
        conditionBoundedBuffer.put(3);
        value = conditionBoundedBuffer.take();
        log.info("{}", value);
        exec.shutdown();
    }
}
