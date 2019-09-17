package com.shadeien.dubbo;

import java.util.ArrayList;
import java.util.List;

public class TclassTest {
    public static void main(String[] args) {
        List nonList = new ArrayList();
        nonList.add("a");
        nonList.add(1);

        List<String> stringList = new ArrayList<>();
//        stringList = nonList;
        stringList.add("a");
        List<?> list = new ArrayList<>(stringList);
        list = stringList;
        list.forEach(o -> System.out.println(o));
//        list.add(1);
//        list.add("1");

        List<Object> objectList = new ArrayList<>(list);
//        objectList = list;
        objectList.add("a1");
        objectList.add(11);
        objectList.add(list);
        objectList.forEach(o -> System.out.println(o));
    }
}
