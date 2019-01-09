package com.shadeien.concurrent;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HiddenIterator {
    final Set<Integer> set = new HashSet<>();

    public synchronized void add(Integer i) {
        set.add(i);
    }

    public void addTenThings() {
        Random r = new Random();
        for (int i = 0; i<100; i++) {
            add(r.nextInt());
        }

        System.out.println(set);
    }
}
