package com.shadeien.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class PutTakeTest {
    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private final AtomicInteger putSum = new AtomicInteger(0);
    private final AtomicInteger takeSum = new AtomicInteger(0);
    private final CyclicBarrier barrier;
    private final BoundedBuffer<Integer> bb;
    private final  int nTrials, nPairs;
    private BarrierTimer timer;

    public static void main(String[] args) throws InterruptedException {
//        new PutTakeTest(10, 10, 10000).test();
//        pool.shutdown();
        int tpt = 1000;
        for (int cap = 1; cap <= 1000; cap*=10) {
            log.info("capacity:{}", cap);
            for (int pairs = 1; pairs <=128; pairs*=2) {
                PutTakeTest test = new PutTakeTest(cap, pairs, tpt);
                test.test();
                Thread.sleep(1000);
                test.test();
                Thread.sleep(1000);
            }
        }
        pool.shutdown();
    }

    public PutTakeTest(int capacity, int npairs, int ntrials) {
        this.bb = new BoundedBuffer<>(capacity);
        this.nTrials = ntrials;
        this.nPairs = npairs;
        this.timer = new BarrierTimer();
        this.barrier = new CyclicBarrier(npairs*2+1, timer);
    }

    void test() {
        try {
            timer.clear();
            for (int i = 0; i < nPairs; i++) {
                pool.execute(new Producer());
                pool.execute(new Consumer());
            }
//            log.info("main start await");
            barrier.await();
//            barrier.reset();
//            log.info("main done await");
            barrier.await();
            log.info("put:{}, take:{}", putSum.get(), takeSum.get());
            long nsPerItem = timer.getTime() / (nPairs*(long)nTrials);
            log.info("nsPerItem:{}", nsPerItem);
//            assertEquals(putSum.get(), takeSum.get());
        } catch (Exception e) {

        }
    }

    int xorshift(int y) {
        y  ^= (y<<6);
        y  ^= (y>>>21);
        y  ^= (y<<7);

        return y;
    }

    class Producer implements Runnable {

        @Override
        public void run() {
            try {
                int seed =  (this.hashCode() ^ (int)System.nanoTime());
                int sum = 0;
                barrier.await();
                for (int i = nTrials; i >0; --i) {
                    bb.put(seed);
                    sum += seed;
                    seed = xorshift(seed);
                }
                putSum.getAndAdd(sum);
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
    class Consumer implements Runnable {

        @Override
        public void run() {
            try {
                barrier.await();
                int sum = 0;
                for (int i = nTrials; i >0 ; --i) {
                    sum += bb.take();
                }
                takeSum.getAndAdd(sum);
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }
    }

    class BarrierTimer implements Runnable {
        private boolean started;
        private long startTime, endTime;

        @Override
        public synchronized void run() {
            long t = System.nanoTime();
            if (!started) {
                started = true;
                startTime = t;
            } else {
                endTime = t;
            }
        }

        public synchronized void clear() {
            started = false;
        }

        public synchronized long getTime() {
            return endTime - startTime;
        }
    }
}
