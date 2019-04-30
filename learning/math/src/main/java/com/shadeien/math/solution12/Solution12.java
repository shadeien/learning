package com.shadeien.math.solution12;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class Solution12 {
    static ArrayList book = new ArrayList();
    static int sum;
    static int[][] map = {
            {0,1,1,-1,1},
            {1,0,-1,1,-1},
            {1,-1,0,-1,1},
            {-1,1,-1,0,-1},
            {1,-1,1,-1,0},
    };
    public static void main(String[] args) {
//        book.add(0);
//        dfs(0);
        bfs();
//        for (Object i : book) {
//            int num = (int) i;
//            log.info("{}", ++num);
//        }
    }


    public static void dfs(int cur) {
        log.info("{}", cur+1);
        sum++;
        if (cur>=map[0].length) {
            return;
        }
        for (int i=cur; i<map[0].length;i++) {
            int value = map[cur][i];
            if (value==1) {
                map[cur][i]=2;
                dfs(i);
//                map[cur][i]=1;
            }
        }
    }

    public static void bfs() {
        for (int j=0; j<map.length; j++) {
            for (int i = j; i < map.length; i++) {
                int value = map[j][i];
                if (value == 1) {
                    log.info("{}", i);
                }
            }
        }
    }
}
