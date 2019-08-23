package com.shadeien.gateway;

import org.springframework.beans.factory.DisposableBean;


public class Student1 implements DisposableBean {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("do destroy");
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
