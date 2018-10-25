package com.juja.array;

import java.util.Arrays;

public class MathUtils {

    /**
     * a*a + b*b <= c
     * a > 0
     * b > 0
     * Приятного аппетита.
     * @param max - c
     * @return count
     */
    public static int lookFor(int max) {
        int result = 0;
        for (int i = 1; i <= max / 2; i++) {
            for (int j = 1; j <= max / 2; j++) {

                if ((i * i + j * j) <= max) {
                    result++;
                }
            }
        }
        return result;
    }

    /**
     * lookFor([1, 1, 1]) = [0, 2]
     * lookFor([0, 1, 1]) = [1, 2]
     * lookFor([1, 1, 0]) = [0, 1]
     * lookFor([0, -100, 1, 1, 0, -1]) = [2, 3]
     * lookFor([1, 1, 0, 1, 1]) = [3, 4] // возвращаем правый
     * lookFor([0, -1, 0, -1]) = [] // отсутствуют положительные числа.
     * @param array - com.juja.array
     * @return result
     */
    public static int[] lookFor(int[] array) {
        int left = 0;
        int right = 0;
        int count = 0;

        int tmpLeft = -1;
        int tmpRight = 0;
        int tmpCount = 0;

        for (int i = 0; i < array.length ; i++) {
           if (array[i] > 0) {
               if (tmpLeft == -1) {
                   tmpLeft = i;
               }
               tmpRight = i;
               tmpCount++;
           }
            if (tmpCount >= count) {
                left = tmpLeft;
                right = tmpRight;
                count = tmpCount;
            }
           if (array[i] <= 0){
               tmpCount = 0;
               tmpLeft = -1;
               tmpRight = 0;
           }

        }

        if (count > 0) {
            return new int[]{left, right};
        }
        return new int[0];
    }

    /**
     * com.juja.Main.
     * @param args - arg
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(lookFor(new int[]{1, 1, 0, 1, 1})));
    }
}
