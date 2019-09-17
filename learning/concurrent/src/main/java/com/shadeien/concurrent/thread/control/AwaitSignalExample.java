package com.shadeien.concurrent.thread.control;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitSignalExample {
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void doWait() {
        lock.lock();
        try {
            System.out.println("await");
            condition.await();
            System.out.println("await done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void doSignal() {
        lock.lock();
        condition.signal();
        System.out.println("doSignal");
        lock.unlock();
    }

    public void doSignalAll() {
        lock.lock();
        condition.signalAll();
        System.out.println("doSignalAll");
        lock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        AwaitSignalExample example = new AwaitSignalExample();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> example.doWait());
        executorService.execute(() -> example.doSignal());

        executorService.shutdown();
    }
}
