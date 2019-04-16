package com.shadeien.leetcode;

public class MethodTest {

    public static void main(String[] args) {
        Integer integer = 1;
        Integer integer1 = null;
        Object object = "string";
        Object object1 = null;
        doSomething(integer);
        doSomething(integer1);
        doSomething(object);
        doSomething(object1);
        doSomething(null);

    }

    public static void doSomething(Object integer) {
        System.out.println("doSomething2");
        if (null == integer) {
            System.out.println("is null");
        } else {
            System.out.println(integer);
        }
    }

    public static void doSomething(Integer integer) {
        System.out.println("doSomething1");
        if (null == integer) {
            System.out.println("is null");
        } else {
            System.out.println(integer);
        }
    }
}
