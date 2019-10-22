package com.shadeien.math.sort.solution1;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class Solution1 {

    /**
     * 桶排序
     * 时间复杂度 O(2*(m+n))
     * 空间复杂度  O(n)
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] sort = {5,3,5,2,8};

        int[] numbs = new int[11];
        Arrays.fill(numbs, 0);

        for (int i : sort) {
            numbs[i]++;
        }

        for (int j=numbs.length-1; j>=0; j--) {
            int data = numbs[j];
            if (data != 0) {
                for (int k=0; k<data; k++)
                    log.info("{}", j);
            }
        }
    }

}
