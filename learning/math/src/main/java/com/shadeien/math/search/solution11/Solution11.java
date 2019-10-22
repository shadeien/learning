package com.shadeien.math.search.solution11;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 层层递进——广度优先搜索
 */
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
//        bfs();
//        log.info("{}", min);
        int[] user = {10001043,10001197,10001210,10001246,10001685,10003482,10001148,10001134,10001118,10001163,10001171,10001186,
                10001183,10001123,10001096,10001120,10001162,10001125,10001180,10001153,10001161,10001105,10001092,
                10001159,10001166,10001167,10001170,10001176,10001164,10001173,10001175,10001172,10001146,10001111,10001081,10001067,
                10001083,10001080,10001077,10001089,10001115,10001084,10001078,10001138,10001103,10001068,10001152,10001190,10001187,
                10001114,10001086,10001160,10001188,10001095,10001165,10001124,10001307,10001303,10001306,10001039,641972285,10001066,
                10001150,10001109,10001137,10001174,10001189,10001194,10001131,10001126,10001104,10001112,899128757,10001157,10001079,
                10001139,10001023,10001070,10001136,10001129,10001108,10001093,10001149,10001098,10001132,10001102,10001113,10001094,
                10001097,10001088,10001184,10001143,10001128,10001107,10001151,10001101,10001145,10001147,10001110,10001135,10001100};
        StringBuilder sb = new StringBuilder();
        for (int id : user) {
            sb.append("(f_getukid(),");
            sb.append(id);
            sb.append(",'1',1000360001,'1','1',NULL,NULL,'2018-05-28 00:00:00','2100-01-01 00:00:00',now( ),now( ),NULL,'10000000','1','wwwarehouse' ),");
        }
        System.out.println(sb.toString());
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
