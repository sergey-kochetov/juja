package com.juja.patterns.templatemethod.sample;

import java.util.Arrays;

public abstract class BubbleSorter {

    public final Object[] sort(Object[] input) {
        Object[] result = new Object[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = input[i];
        }
        for (int c = 0; c < input.length - 1; c++) {
            for (int d = 0; d < input.length - c - 1; d++) {
                if (compare(result[d], result[d + 1])) {
                    Object swap = result[d];
                    result[d] = result[d + 1];
                    result[d + 1] = swap;
                }
            }
        }
        return result;
    }
    // а тут клиент определяет как мы будем сортировать
    protected abstract boolean compare(Object o1, Object o2);
}
