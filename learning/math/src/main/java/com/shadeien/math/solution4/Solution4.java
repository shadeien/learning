package com.shadeien.math.solution4;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 解密 QQ 号——队列
 */
@Slf4j
public class Solution4 {

    public static void main(String[] args) {
        Integer[] sort ={6,3,1,7,5,8,9,2,4};

        Queue queue = new ConcurrentLinkedQueue(Arrays.asList(sort));
        int i = 0;

        Object data;
        while ((data=queue.poll()) != null) {
            i++;
            if (i%2 == 0) {
                queue.add(data);
            } else {
                log.info("{}", data);
            }
        }
    }

    public static void puzzle(int[] sort) {
        int[] all = new int[(sort.length<<1)];
        for (int i=0; i<sort.length; i++) {
            all[i] = sort[i];
        }
        int head = 0;
        int tail = sort.length;
        while (head<tail) {
            log.info("{}", all[head]);
            head++;
            //先将新队首的数添加到队尾
            all[tail]=all[head];
            tail++;
            //再将队首出队
            head++;
        }
        log.info("size:{}, tail:{}", sort.length, tail-1);
    }
}
