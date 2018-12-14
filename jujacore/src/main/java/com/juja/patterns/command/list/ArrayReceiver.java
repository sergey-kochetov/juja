package com.juja.patterns.command.list;

import java.util.Arrays;

public class ArrayReceiver {
    // это мы храним
    private String[] array = new String[0];

    // а это разрешаем делать нашим клиентам
    public void copyOf(int newLength) {
        array = Arrays.copyOf(array, newLength);
    }

    public String get(int index) {
        return array[index];
    }

    public void set(int index, String data) {
        array[index] = data;
    }

    public int size() {
        return array.length;
    }
}
