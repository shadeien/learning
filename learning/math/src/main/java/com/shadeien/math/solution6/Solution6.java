package com.shadeien.math.solution6;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 纸牌游戏——小猫钓鱼
 */
@Slf4j
public class Solution6 {

    public static void main(String[] args) {
        Integer[] a = {2, 4, 1, 2, 5, 6};
        Integer[] b = {3, 1, 3, 5, 6, 4};

        Queue queueA = new ConcurrentLinkedQueue(Arrays.asList(a));
        Queue queueB = new ConcurrentLinkedQueue(Arrays.asList(b));

        int[] mark = new int[10]; //0-9的桶
        Stack stack = new Stack();
        int i = 0;
        while (!queueA.isEmpty() && !queueB.isEmpty()) {
            int value;
            if (i%2==0) {
                value = (int) queueA.poll();
            } else {
                value = (int) queueB.poll();
            }
            int markTimes = ++mark[value];
            stack.push(value);
            if (markTimes > 1) {
                Queue addQueue;
                if (i%2==0) {
                    addQueue = queueA;
                } else {
                    addQueue = queueB;
                }
                popUntilNum(value, stack, addQueue, mark);
            }
            i++;
        }
        if (queueA.isEmpty()) {
            log.info("小哈 win");
        }
        if (queueB.isEmpty()) {
            log.info("小哼 win");
        }
    }

    public static void popUntilNum(int num, Stack stack, Queue addQueue, int[] mark) {
        int popNum = (int) stack.pop();
        addQueue.add(popNum);
        --mark[popNum];
        while (!stack.peek().equals(num)) {
            popNum = (int) stack.pop();
            addQueue.add(popNum);
            --mark[popNum];
        }
//        addQueue.add(popNum);
//        --mark[popNum];
    }
}
