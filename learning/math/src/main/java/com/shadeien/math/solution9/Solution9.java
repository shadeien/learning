package com.shadeien.math.solution9;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Slf4j
public class Solution9 {
    static int[] a = {6, 2, 5, 5, 4, 5, 6, 3, 7, 6};
    // m<=24, A+B=C, 20根，2*b*2<20  so b<5 1111
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        Set<String> list = new HashSet<>();
        int max = 1111;
        for (int i=0; i<=max; i++) {
            int aCount = getCount(i);
            if (aCount > m) {
                break;
            }
            for (int j=0; j<=max; j++) {
                int bCount = getCount(j);
                int sumCount = getCount(i+j);
                if (aCount + bCount + sumCount == m-4) {
                    list.add(i+" + "+j+" = "+(i+j));
                }
            }
        }
        log.info("size:{}, {}", list.size(), list);
    }

    public static int getCount(int i) {
        int count = 0;
        if (i == 0) {
            return a[0];
        }

        while (i!=0) {
            int b = i % 10;
            count += a[b];
            i = i/10;
        }

        return count;
    }
}
