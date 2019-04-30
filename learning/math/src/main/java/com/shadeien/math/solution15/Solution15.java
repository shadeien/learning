package com.shadeien.math.solution15;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Solution15 {
    static int min = 99999;
    static int[][] map = {
            {0,2,6,4},
            {99999,0,3,99999},
            {7,99999,0,1},
            {5,99999,12,0},
    };
    public static void main(String[] args) {
        int n = map.length;
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int newLength = map[i][k] + map[k][j];
                    if (map[i][j] > newLength) {
                        map[i][j] = newLength;
                    }
                }
            }
        }
        for (int l=0;l<n;l++)
            log.info("{}", map[l]);
    }

}
