package com.shadeien.hessain;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    int age;
    String name;

    String remark;
}
