package com.shadeien.leetcode;

import java.util.Stack;

public class MyQueue {
    Stack<Integer> in = new Stack<Integer>();
    Stack<Integer> out = new Stack<Integer>();

    public void push(int node) {
        in.push(node);
    }

    public int pop() throws Exception {
        if (out.isEmpty())
            while (!in.isEmpty())
                out.push(in.pop());

        if (out.isEmpty())
            throw new Exception("queue is empty");

        return out.pop();
    }

    public static void main(String[] args) throws Exception {
        MyQueue queue = new MyQueue();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        int data = queue.pop();
        System.out.println(data);
        data = queue.pop();
        System.out.println(data);
        queue.push(5);
        data = queue.pop();
        System.out.println(data);
        queue.push(4);
        data = queue.pop();
        System.out.println(data);

    }
}
