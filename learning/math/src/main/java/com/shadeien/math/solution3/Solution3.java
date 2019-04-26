package com.shadeien.math.solution3;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Solution3 {

    //快速排序的最差时间复杂度和冒泡排序是一样的，都是 O(N2)，它的平均时间复杂度为 O (NlogN)
    public static void main(String[] args) {
        int[] sort = {6,1,2,7,9,3,4,5,10,8};

        quickSort(sort, 0, sort.length-1);

        for (int k=sort.length-1; k>=0; k--) {
            log.info("{}", sort[k]);
        }
    }

    // 二分排序
    public static void quickSort(int[] sort, int left, int right) {
        if (left > right)
            return;
        int key = sort[left];
        int i = left;
        int j = right;
        while (i != j) {
            while (sort[j]>=key && j>i) {
                j--;
            }

            while (sort[i]<=key && j>i) {
                i++;
            }
            if (i<j) {
                int temp = sort[j];
                sort[j] = sort[i];
                sort[i] = temp;
            }
        }
        sort[left] = sort[i];
        sort[i] = key;

        quickSort(sort, left, i-1);
        quickSort(sort, i+1, right);
    }
}
