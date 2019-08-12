package com.shadeien.math.collections;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

@Slf4j
public class ListIteratorDemo {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>(20);
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.stream().distinct().forEach(str -> log.info(str));
        ListIterator<String> it = list.listIterator();
        it.next();
        it.next();
        it.set("c");

//        Collections.shuffle(list);
//        Collections.reverse(list);
//        Collections.rotate(list, 3);
        int s = Collections.binarySearch(list, "c");
        log.info("list:{}", list);
        log.info("it:{}", s);

    }
}
