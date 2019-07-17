package com.shadeien.dubbo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Model<T> implements Serializable {
    List<T> list;
}
