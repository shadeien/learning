package com.shadeien.concurrent;

public class ThreadGate {
    private boolean isOpen;
    private int generation;

    public synchronized void close() {
        isOpen = false;
    }

    public synchronized void open() {
        ++generation;
        isOpen = true;
        notifyAll();
    }

    public synchronized void await() {
        int arrivalGeneration = generation;
        while (!isOpen && arrivalGeneration == generation) {
            await();
        }
    }
}
