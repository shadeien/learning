package com.shadeien.math.search;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BinaryTree {
    static int[] h = {99, 5, 36, 7, 22, 17, 46, 12, 2, 19, 25, 28, 1, 92};
    static int n = h.length-1;
    public static void main(String[] args) {
        create();

        log.info("{}", h);
        for (int i = 1; i <= n; i++) {
            log.info("{}", deleteMax());
        }
    }

    static int deleteMax() {
        int t = h[1];
        h[1] = h[n];
        n--;
        siftdown(t);
        
        return t;
    }

    static void create() {
        for (int i=n/2; i>=1; i--) {
            siftdown(i);
        }
    }

    private static void siftdown(int i) {
        int t, flag=0;
        while (i*2<=n && flag == 0) {
            if (h[i]>h[i*2]) {
                t = i*2;
            } else {
                t = i;
            }
            if (i*2+1 <= n) {
                if (h[i] > h[i * 2 + 1]) {
                    t = i * 2 + 1;
                }
            }
            if (t != i) {
                swap(t, i);
                i=t;
            } else {
                flag = 1;
            }
        }
    }

    static void siftUp(int i) {
        int flag = 0;
        if (i == 1) return;
        while (i != 1 && flag ==0) {
            if (h[i]<h[i/2]) {
                swap(i, i/2);
            } else {
                flag = 1;
            }
            i = i/2;
        }
    }

    private static void swap(int x, int y) {
        int t = h[x];
        h[x] = h[y];
        h[y] = t;
    }
}
