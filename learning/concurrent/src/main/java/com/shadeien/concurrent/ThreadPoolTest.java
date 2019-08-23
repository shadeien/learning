package com.shadeien.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class ThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {
        TestingThreadFactory2 threadFactory2 = new TestingThreadFactory2();
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(2);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4,
                10, TimeUnit.SECONDS, linkedBlockingQueue, threadFactory2, new MyRejectedExecutionHandler());

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1, threadFactory2);
        scheduledExecutorService.scheduleAtFixedRate(()->log.info("scheduledExecutorService:{}", executor), 0, 10, TimeUnit.SECONDS);

        RunAlways always = new RunAlways();
        RunOneTime oneTime = new RunOneTime();
        executor.execute(always);
        log.info("{}",executor);
        executor.execute(always);
        log.info("{}",executor);
        executor.execute(oneTime);
        executor.execute(oneTime);
        log.info("{}",executor);
        executor.execute(oneTime);
        executor.execute(oneTime);
        executor.execute(oneTime);
        log.info("{}",executor);

        executor.execute(oneTime);
        log.info("{}",executor);
        executor.execute(always);
        log.info("{}",executor);

    }

    static class RunAlways implements Runnable {

        @Override
        public void run() {
            while (true) {
            }
        }
    }

    static class RunOneTime implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("RunOneTime");
        }
    }
}
