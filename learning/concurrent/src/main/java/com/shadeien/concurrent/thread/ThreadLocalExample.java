package com.shadeien.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalExample {
    ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public Integer getSeqNum() {
        seqNum.set(seqNum.get()+1);
        Integer i = seqNum.get();
        log.info("{}", i);
        return i;
    }

    public static void main(String[] args) {
        ThreadLocalExample example = new ThreadLocalExample();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                example.getSeqNum();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                example.getSeqNum();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                example.getSeqNum();
            }
        }).start();
    }
}
