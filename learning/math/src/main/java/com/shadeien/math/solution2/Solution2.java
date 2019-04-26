package com.shadeien.math.solution2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Solution2 {

    // 冒泡排序 时间复杂度O(Math.pow(N,2)) N的平方 浪费时间
    public static void main(String[] args) {
        int[] sort = {8,100,50,22,15,6,1,1000,999,0};

        int maxJ = 0;
        int maxI = 0;
        for (int j =1; j<sort.length; j++) {
            for (int i = 0; i < sort.length-1; i++) {
                if (sort[i] > sort[i + 1]) {
                    int temp = sort[i];
                    sort[i] = sort[i + 1];
                    sort[i + 1] = temp;
                }
                maxI = i;
            }
            maxJ = j;
        }

        for (int j=sort.length-1; j>=0; j--) {
            log.info("{}", sort[j]);
        }
        log.info("maxJ:{}, maxI:{}", maxJ, maxI);
    }
}
