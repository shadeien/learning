package com.shadeien.concurrent;


interface A {
    void do1();
}

interface B {
    void do2();
}

interface C extends A,B {
    void do3();
}

public class ExtendTest implements C {
    @Override
    public void do1() {

    }

    @Override
    public void do2() {

    }

    @Override
    public void do3() {

    }
}
