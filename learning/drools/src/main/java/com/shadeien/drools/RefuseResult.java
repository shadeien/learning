package com.shadeien.drools;

import lombok.Data;

import java.io.Serializable;

@Data
public class RefuseResult implements Serializable {

    private boolean isOpen;
    private Object data;
}