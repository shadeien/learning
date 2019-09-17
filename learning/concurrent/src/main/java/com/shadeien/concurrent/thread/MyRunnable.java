package com.shadeien.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyRunnable implements Runnable {

    RetObject retObject;

    public MyRunnable(RetObject retObject) {
        this.retObject = retObject;
    }

    public RetObject getRetObject() {
        return retObject;
    }

    @Override
    public void run() {
        log.info("do run");
        retObject.setName("run done");
    }
}
