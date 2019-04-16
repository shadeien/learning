package com.shadeien.concurrent;

import java.util.concurrent.*;

public class ForkJoinTest {

    private static class ForkJoinTask extends RecursiveTask<Integer> {
        private int first;
        private int last;

        public ForkJoinTask(int first, int last) {
            this.first = first;
            this.last = last;
        }

        @Override
        protected Integer compute() {
            int subCount;
            if (last - first < 10) {
                subCount = 0;
                for (int i=first; i<=last; i++) {
                        subCount+=i;
                }
            } else {
                int mid = (first + last) >>> 1;
                ForkJoinTask left = new ForkJoinTask(first, mid);
                left.fork();
                ForkJoinTask right = new ForkJoinTask(mid+1, last);
                right.fork();
                subCount = left.join();
                subCount += right.join();
            }
            return subCount;
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask task = new ForkJoinTask(0, 1000000);
        long start = System.currentTimeMillis();
        int n = pool.invoke(task);
        System.out.println(n+" end diff:"+ (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        int total = 0;
        for (int i=0; i<=1000000; i++) {
            total +=i;
        }
        System.out.println(total+" end diff:"+ (System.currentTimeMillis() - start));
    }

}
