package com.shadeien.math.solution13;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Solution13 {
    static int min = 20;
    static int[][] map = {
            {0,2,-1,-1,10},
            {-1,0,3,-1,7},
            {4,-1,0,4,-1},
            {-1,-1,-1,0,5},
            {-1,-1,3,-1,0},
    };
    static int[][] map2 = {
            {0,2,4,-1,10},
            {2,0,3,-1,7},
            {4,3,0,4,3},
            {-1,-1,4,0,5},
            {-1,7,3,5,0},
    };
    static int[] book = new int[map.length];
    public static void main(String[] args) {
        book[0]=1;
        dfs2(0,0);
        log.info("{}", min);
//        bfs();
//        for (Object i : book) {
//            int num = (int) i;
//            log.info("{}", ++num);
//        }
    }


    public static void dfs(int cur, int dist) {
        if (cur==map.length-1) {
            if (min > dist) {
                min = dist;
            }
            return;
        }
        for (int i=0; i<map.length;i++) {
            int value = map[cur][i];
            if (value!=-1 && book[i] ==0) {
                book[i]=1;
                dfs(i, dist+value);
                book[i]=0;
            }
        }
    }

    public static void dfs2(int cur, int dist) {
        if (cur==map2.length-1) {
            if (min > dist) {
                min = dist;
            }
            return;
        }
        for (int i=0; i<map2.length;i++) {
            int value = map2[cur][i];
            if (value!=-1 && book[i] ==0) {
                book[i]=1;
                dfs2(i, dist+value);
                book[i]=0;
            }
        }
    }

}
