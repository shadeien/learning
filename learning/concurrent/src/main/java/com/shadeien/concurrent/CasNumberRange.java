package com.shadeien.concurrent;

import lombok.Data;

import java.util.concurrent.atomic.AtomicReference;

public class CasNumberRange {
    private final AtomicReference<IntPair> values = new AtomicReference<>(new IntPair(0,0));

    public int getLower() {
        return values.get().lower;
    }
    public int getUpper() {
        return values.get().upper;
    }

    public void setLower(int i) {
        while (true) {
            IntPair oldv = values.get();
            if (i > oldv.upper) {
                throw new IllegalArgumentException("bigger than upper");
            }
            IntPair newv = new IntPair(i, oldv.upper);
            if (values.compareAndSet(oldv, newv))
                return;
        }
    }

    public void setUpper(int i) {
        while (true) {
            IntPair oldv = values.get();
            if (i > oldv.lower) {
                throw new IllegalArgumentException("smaller than lower");
            }
            IntPair newv = new IntPair(oldv.lower, i);
            if (values.compareAndSet(oldv, newv)) {
                return;
            }
        }
    }

    @Data
    private static class IntPair {
        final int lower;
        final int upper;
    }

}
