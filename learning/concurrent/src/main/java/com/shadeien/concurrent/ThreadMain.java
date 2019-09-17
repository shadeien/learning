package com.shadeien.concurrent;

import com.shadeien.concurrent.thread.MyCallable;
import com.shadeien.concurrent.thread.MyRunnable;
import com.shadeien.concurrent.thread.RetObject;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class ThreadMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable callable = new MyCallable();
        RetObject ret = new RetObject();
        MyRunnable runnable = new MyRunnable(ret);

        Thread runThread = new Thread(runnable);
        runThread.start();
        log.info("name:{}", runnable.getRetObject());

        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread callThread = new Thread(futureTask);
        callThread.start();
        log.info("future:{}", futureTask.get());

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<?> future = executorService.submit(runnable);
        log.info("future:{}", future.get());
        log.info("name:{}", runnable.getRetObject());


        Future<RetObject> submit = executorService.submit(runnable, ret);
        log.info("submit:{} ret:{}", submit.get(), ret);
        log.info("name:{}", runnable.getRetObject());

        executorService.shutdown();
    }
}
