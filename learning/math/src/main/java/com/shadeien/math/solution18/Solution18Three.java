package com.shadeien.math.solution18;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class Solution18Three {
    static int[] num;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        num = new int[n];
        for (int i=1;i<=n;i++) {
            num[i-1] = i;
        }
        int m = scanner.nextInt();
        int prev = 0;
        for (int i=0;i<(m<<1);i++) {
            int tmp = scanner.nextInt();
            if (i%2==1) {
                merge(prev, tmp);
            } else {
                prev = tmp;
            }
        }
        int count = 0;
        for (int i=0; i<n;i++) {
            if (num[i] == i+1)
                count++;
        }
        log.info("{}", count);
    }

    static int getf(int index) {
        int value = num[index-1];
        if (index == value) {
            return index;
        } else {
            num[index-1] = getf(value);
            return num[index-1];
        }
    }

    private static void merge(int prev, int tmp) {
        int t1 = getf(prev);
        int t2 = getf(tmp);
        if (t1!=t2) {
            num[t2-1] = t1;
        }
    }
}
