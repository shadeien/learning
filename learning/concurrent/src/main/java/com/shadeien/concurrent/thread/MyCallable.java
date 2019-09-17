package com.shadeien.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

@Slf4j
public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        log.info("do call");
        return "call done";
    }
}
