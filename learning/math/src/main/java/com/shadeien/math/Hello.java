package com.shadeien.math;

public class Hello {
    public void test() {
        int i = 8;
        while ((i -= 3) > 0);
        System.out.println("i = " + i);
    }

    public static void main(String[] args) {
        Hello hello = new Hello();
        for (int i = 0; i < 50_000; i++) {
            hello.test();
        }
    }
}
