package com.juja.patterns.templatemethod.sample;

import com.juja.core.array.Arr;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        BubbleSorter sorter = new ToStringLengthBubbleSorter();

        System.out.println(Arrays.toString(sorter.sort(
                new Integer[]{12, 423, 23, 5, 12423, 34}
        )));

        System.out.println(Arrays.toString(sorter.sort(
                new String[] {"qwert", "asdf", "zxc", "12", "1", "5"}
        )));

        System.out.println();
    }
}
