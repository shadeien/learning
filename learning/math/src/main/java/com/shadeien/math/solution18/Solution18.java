package com.shadeien.math.solution18;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Scanner;

@Slf4j
public class Solution18 {
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
            if (node.list != null) {
                listLength += node.list.size();
            }
        }
        log.info("{}", n-listLength);
    }

    private static void merge(int prev, int tmp) {
        Node node1 = num[prev-1];
        Node node2 = num[tmp-1];
        if (node1.value == prev && node2.value == tmp) {
            if (node1.list == null) {
                node1.list = new LinkedList();
            }
            if(node2.list != null) {
                node1.list.addAll(node2.list);
                node2.list = null;
            }
            node1.list.add(node2.value);
            node2.value = node1.value;
        } else if (node1.value != prev && node2.value == tmp) {
            Node parent = num[node1.value - 1];
            if(node2.list != null) {
                node1.list.addAll(node2.list);
                node2.list = null;
            }
            parent.list.add(node2.value);
            node2.value = parent.value;
        } else if (node1.value == prev && node2.value != tmp) {
            Node parent = num[node2.value - 1];
            if(node1.list != null) {
                parent.list.addAll(node1.list);
                node1.list = null;
            }
            parent.list.add(node1.value);
            node1.value = parent.value;
        }
    }

    @Data
    static class Node {
        int value;
        LinkedList list;
    }

}
