package com.shadeien.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Slf4j
public class ExecutorServiceMain {

    public static void main(String[] args) throws Exception {
        rejectHandle();
//        invokeAll();
//        invokeAny();
//        schedule();
    }

    public static void rejectHandle() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10), new MyRejectedExecutionHandler());
        for (int i=0; i<100; i++) {
            int finalI = i;
            executor.execute(() -> {
                System.out.println("MyRejectedExecutionHandler:"+ finalI)   ;
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }

    public static void schedule() throws InterruptedException {
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        // 在上一个任务开始的时候就开始计时，不管上个任务是否完成都会开始新的任务
//        exec.scheduleAtFixedRate(() -> System.out.println("fixedRate"), 0, 1000, TimeUnit.MILLISECONDS);
        // 在上一个任务完成后,才开始下个任务的计时
//        exec.scheduleWithFixedDelay(() -> System.out.println("FixedDelay"), 0, 1000, TimeUnit.MILLISECONDS);
        exec.schedule(() -> System.out.println("FixedDelay"), 1000, TimeUnit.MILLISECONDS);
        Thread.sleep(10000);
        exec.shutdown();
    }

    public static void invokeAll() throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        ArrayList<Task> list = new ArrayList<>();
        for (int i=0; i<10; i++) {
            list.add(new Task(i));
        }
        List<Future<Integer>> res = exec.invokeAll(list);

        List<Integer> result = res.parallelStream().map((future) -> {
            try {
                return future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        
        log.info("invokeAll:{}", result);
        exec.shutdown();
    }
    public static void invokeAny() throws InterruptedException, ExecutionException {
        ExecutorService exec = Executors.newCachedThreadPool();
        ArrayList<Task> list = new ArrayList<>();
        for (int i=0; i<10; i++) {
            list.add(new Task(i));
        }
        Integer res = exec.invokeAny(list);

        log.info("invokeAny:{}", res);
        exec.shutdown();
    }

    static class Task implements Callable<Integer> {

        int i;

        public Task(int index) {
            i = index;
        }

        @Override
        public Integer call() throws Exception {
            if (i == 0) {
                Thread.sleep(1000);
            }
            return ++i;
        }
    }
}
