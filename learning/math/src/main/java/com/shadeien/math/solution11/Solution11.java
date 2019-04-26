package com.shadeien.math.solution11;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;

@Slf4j
public class Solution11 {
    static ArrayList book = new ArrayList();
    static int[][] direction = {{0,1}, {0,-1}, {1,0}, {-1,0}};
    static int[][] map = {
            {0,0,1,0},
            {0,0,0,0},
            {0,0,1,0},
            {0,1,-1,0},
            {0,0,0,1},
    };
    static int min=20;
    public static void main(String[] args) {
//        map[0][0] =2;
//        dfs(0,0,1);
        bfs();
        log.info("{}", min);
    }

    @Data
    static
    class Node {
        int x;
        int y;
        Node f;
        int step;
    }

    public static void bfs() {
        Node minNode = null;
        LinkedList<Node> linkedList = new LinkedList();
        Node start = new Node();
        start.setX(0);
        start.setY(0);
        start.setStep(0);
        linkedList.add(start);

        while (linkedList.peek() != null) {
            Node node = linkedList.pop();
            int step = node.getStep();
            step++;
            for (int[] direct : direction) {
                int tx = node.getX() + direct[0];
                int ty = node.getY() + direct[1];
                if (tx<0 || tx >= map.length || ty<0 || ty>=map[0].length)
                    continue;
                if (map[tx][ty] == -1) {
                    if (step < min) {
                        min = step;
                        minNode = node;
                    }
                }
                if (map[tx][ty] == 0) {
                    map[tx][ty] = 2;
                    Node currentNode = new Node();
                    currentNode.setX(tx);
                    currentNode.setY(ty);
                    currentNode.setStep(step);
                    currentNode.setF(node);
                    linkedList.add(currentNode);
                }
            }

        }

        while (minNode.getF() != null) {
            log.info("{}", minNode);
            minNode = minNode.getF();
        }
    }

    public static void dfs(int x, int y, int currentCount) {

        for (int[] step : direction) {
            int tx = x+step[0];
            int ty = y+step[1];
            if (tx<0 || tx >= map.length || ty<0 || ty>=map[0].length)
                continue;
            if (map[tx][ty] == -1) {
                if (currentCount < min) {
                    min = currentCount;
                }
                return;
            }
            if (map[tx][ty] == 0) {
                map[tx][ty] = 2;
                currentCount++;
                dfs(tx, ty, currentCount);
                map[tx][ty] = 0;
            }
        }
    }
}
