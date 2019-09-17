package com.shadeien.concurrent.thread.control;

import com.shadeien.concurrent.thread.MyThread;

public class JoinExample {

    public static void main(String[] args) {
        Thread a = new MyThread("A");
        Thread b = new MyThread("B");
        a.start();
        b.start();

        try {
            a.join(1000);
            b.join(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main");
    }

    static class A extends Thread {
        @Override
        public void run() {
            System.out.println("A");
        }
    }

    static class B extends Thread {

        Thread a;

        public B(Thread a) {
            this.a = a;
        }

        @Override
        public void run() {
            try {
                a.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B");
        }
    }
}
