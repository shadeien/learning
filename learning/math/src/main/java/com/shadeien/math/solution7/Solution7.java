package com.shadeien.math.solution7;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Solution7 {

    public static void main(String[] args) {
        int[] a = {2, 3, 5, 8, 9, 10, 18, 26, 32 };
        int insert = 6;

        Node entry = new Node();

        Node head = null;
        for (int i : a) {
            Node node = new Node();
            node.data = i;
            if (null == head) {
                head = node;
                entry.next = head;
            } else {
                head.next = node;
            }
            head = node;
        }


        while (entry.next != null) {
            log.info("{}", entry.data);
            entry = entry.next;
        }
    }

    @Data
    static
    class Node {
        int data;
        Node next;
    }
}
