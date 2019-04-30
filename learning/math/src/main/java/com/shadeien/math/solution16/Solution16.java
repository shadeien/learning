package com.shadeien.math.solution16;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j
public class Solution16 {
    static int max = 99999;
    static int[][] map = {
            {0,1,12,max, max,max},
            {max,0,9,3, max,max},
            {max,max,0,max, 5,max},
            {max,max,4,0, 13,15},
            {max,max,max,max, 0,4},
            {max,max,max,max, max,0},
    };
    static int[][] map2 = {
            {0,-3,max,max,5},
            {max,0,2,max,max},
            {max,max,0,3,max},
            {max,max,max,0,2},
            {max,max,max,max,0},
    };
    static int[] book = {0,max,max,max, max,max};
    static int[] book2 = {0,max,max,max, max};
    public static void main(String[] args) {
        LinkedList<Node> linkedList = new LinkedList();
        Node start = new Node();
        start.setX(0);
        start.setValue(0);
        linkedList.add(start);

        while (!linkedList.isEmpty()) {
            Node node = linkedList.pop();
            for (int i = 0; i < map[0].length; i++) {
                int x = node.getX();
                int data = map[x][i];
                int result = data + node.getValue();
                if (book[i] > result && data!=0 && data!=max) {
                    book[i] = result;
                    Node newNode = new Node();
                    newNode.setValue(result);
                    newNode.setX(i);
                    linkedList.add(newNode);
                }
            }

        }

        log.info("{}", book);
    }

    @Data
    static class Node {
        int x;
        int value;
    }

}
