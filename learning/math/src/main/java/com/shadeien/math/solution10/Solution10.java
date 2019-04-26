package com.shadeien.math.solution10;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class Solution10 {
    static int n=0;
    static int[] book;
    static int[] a;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        book = new int[n+1];
        a = new int[n+2];

        dfs(1);
    }

    public static void dfs(int step) {

        if (step == n+1) {
            for (int i=1; i<=n; i++) {
                log.info("{}", a[i]);
            }
            return;
        }

        for (int i=1; i<=n; i++) {
            if (book[i] == 0) {
                a[step] = i;
                book[i] = 1;
                dfs(step+1);
                book[i] = 0;
            }
        }
    }
}
