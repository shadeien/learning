package com.shadeien.math.solution14;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j
public class Solution14 {
    static int min = 99999;
    static int[][] map = {
            {0,1,1,-1,-1},
            {-1,0,1,1,-1},
            {1,1,0,1,1},
            {-1,1,1,0,1},
            {-1,-1,1,1,0},
    };
    static int[] book = new int[map.length];
    public static void main(String[] args) {
        book[0]=1;
        bfs();
        log.info("{}", min);
    }

    @Data
    static class Node {
        int x;
        int step;
    }

    static LinkedList<Node> linkedList = new LinkedList();
    public static void bfs() {
        Node start = new Node();
        start.x = 0;
        start.step = 0;
        linkedList.push(start);
        while (!linkedList.isEmpty()) {
            Node node = linkedList.pop();
            int tx = node.x;
            if (tx == map.length-1) {
                if (min > node.step) {
                    min = node.step;
                }
            }
            for (int i=0; i<map[tx].length;i++) {
                int data = map[tx][i];
                if (data == 1 && book[i]==0) {
                    book[i]=1;
                    Node node1 = new Node();
                    node1.setStep(node.step+1);
                    node1.setX(i);
                    linkedList.push(node1);
                }
            }
        }
    }

}
