package com.shadeien.math.solution6;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

public class LinkedListSolution {

    public static void main(String[] args) {
        Integer[] a1 = {2, 4, 1, 2, 5, 6};
        Integer[] b1 = {3, 1, 3, 5, 6, 4};
        //a手牌
        LinkedList<Integer> a = new LinkedList<>(Arrays.asList(a1));
        //b手牌
        LinkedList<Integer> b = new LinkedList<>(Arrays.asList(b1));

        //定义一个栈，用来放置桌面手牌
        Stack<Integer> stack = new Stack();
        System.out.println("游戏开始！");
        int[] book = new int[10];
        int n,t,s;
        //有一人手牌为空即为游戏结束
        while (!a.isEmpty() && !b.isEmpty()) {//当有人手中没牌游戏结束
            t = a.removeFirst();
            if (book[t] == 0) {//A没有赢
                stack.push(t);//桌面上加一张牌
                book[t] = 1;//记录桌面上已经有这张牌了
            }else {//A赢了
                a.addLast(t);//将打出的牌到到末尾
                while (!stack.peek().equals(t)) {//将桌面上的牌按顺序放到A的末尾
                    s = stack.pop();
                    a.addLast(s);
                    book[s] = 0;
                }
            }

            //同上B取出牌
            t = b.removeFirst();
            if (book[t] == 0) {
                stack.push(t);
                book[t] = 1;
            } else {
                b.addLast(t);
                while (!stack.peek().equals(t)) {
                    s = stack.pop();
                    b.addLast(s);
                    book[s]= 0;
                }
            }
            System.out.println("aaa");
        }

        if (!a.isEmpty()) {//A胜利
            System.out.println("A君胜利！A手中的牌是");
            while (!a.isEmpty()) {
                System.out.print(a.removeFirst()+ " ");
            }
            System.out.println();
            if (!stack.isEmpty()) {//桌面上有牌
                System.out.println("桌面上的牌是");
                for (Integer x : stack) {
                    System.out.print(x + " ");
                }
            } else {
                System.out.println("桌面没有牌了");
            }
        }  else {//B胜利
            System.out.println("B君胜利！B手中的牌是");
            while (!b.isEmpty()) {
                System.out.print(b.removeFirst()+ " ");
            }
            System.out.println();
            if (!stack.isEmpty()) {//桌面上有牌
                System.out.println("桌面上的牌是");
                for (Integer x : stack) {
                    System.out.print(x + " ");
                }
            } else {
                System.out.println("桌面没有牌了");
            }
        }
    }
}
