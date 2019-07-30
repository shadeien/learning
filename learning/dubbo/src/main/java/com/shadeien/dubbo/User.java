package com.shadeien.dubbo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = -2845651020098082298L;

    int age;
    String name;

    String remark;
}
