package com.shadeien.math.sort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MergeSort {
    /**
     * 归并排序
     * 时间复杂度：O(nlogn)
     * 空间复杂度：O(N)
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] sort = {6,1,2,7,9,3,4,5,10,8};

        mergeSort(sort, 0, sort.length-1);

        for (int k=sort.length-1; k>=0; k--) {
            log.info("{}", sort[k]);
        }
    }

    static void mergeSort(int[] sort, int i, int j) {
        log.info("{}, {}", i, j);
        if(i<j){//两路归并
            int middle = (i+j)/2;
            mergeSort(sort,i,middle);
            mergeSort(sort, middle+1, j);
            //将两组数据归并
            merge(sort, i, middle, middle+1, j);
        }
    }

    static void merge(int[] sort, int start1, int end1, int start2, int end2) {
        int i,j;
        i = start1;
        j = start2;
        int[] temp = new int[end2-start1+1];//建立一个数组为两个之和大小，归并到这个数组
        int k =0;
        while (i <=end1 && j<= end2) {
            if (sort[i] > sort[j]) {
                temp[k] = sort[j];
                k++;
                j++;
            } else {
                temp[k] = sort[i];
                k++;
                i++;
            }
        }

        while (i<=end1) {
            temp[k] = sort[i];
            k++;
            i++;
        }
        while (j<=end2) {
            temp[k] = sort[j];
            k++;
            j++;
        }
        //将temp中数据转移到原数组中
        for(int element :temp){
            sort[start1] = element;
            start1++;
        }
    }
}
