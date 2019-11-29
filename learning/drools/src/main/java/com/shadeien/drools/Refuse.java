package com.shadeien.drools;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Refuse implements Serializable {

    /**
     * 年龄
     */
    private int age;
    /**
     * 工作城市
     */
    private String workCity;
    /**
     * 申请城市
     */
    private String applyCity;
    /**
     * 居住城市
     */
    private String addressCity;

    private List<String> condition1;
    private List<String> condition2;
}