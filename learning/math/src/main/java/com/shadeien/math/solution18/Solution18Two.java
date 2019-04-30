package com.shadeien.math.solution18;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class Solution18Two {
    static Node[] num;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        num = new Node[n];
        for (int i=1;i<=n;i++) {
            Node node = new Node();
            node.value = i;
            num[i-1] = node;
        }
        int m = scanner.nextInt();
        int prev = 0;
        for (int i=0;i<(m<<1);i++) {
            int tmp = scanner.nextInt();
            if (i%2==1) {
                merge(prev, tmp);
            } else {
                prev = tmp;
            }
        }
        int listLength = 0;
        for (int i=0; i<n;i++) {
            Node node = num[i];
            listLength += node.length;
        }
        log.info("{}", n-listLength);
    }

    @Data
    static class Node {
        int value;
        int length;
    }

    private static void merge(int prev, int tmp) {
        Node node1 = num[prev-1];
        Node node2 = num[tmp-1];
        if (node1.value == prev && node2.value == tmp) {
            node1.length += node2.length;
            node2.length = 0;
            node1.length += 1;
            node2.value = node1.value;
        } else if (node1.value != prev && node2.value == tmp) {
            Node parent = num[node1.value - 1];
            parent.length += node2.length;
            node2.length = 0;
            parent.length += 1;
            node2.value = parent.value;
        } else if (node1.value == prev && node2.value != tmp) {
            Node parent = num[node2.value - 1];
            parent.length += node1.length;
            node1.length = 0;
            parent.length += 1;
            node1.value = parent.value;
        }
    }
}
