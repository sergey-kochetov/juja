package com.juja.core.task;

public class Test001 {
    public static void main(String[] args) {
        Integer intObj = new Integer(10);
        System.out.println(intObj);
        changeValue(intObj);
        System.out.println(intObj);

        int intPrimitive = 20;
        System.out.println(intPrimitive);
        changeValue(intPrimitive);
        System.out.println(intPrimitive);

        Integer intObj2 = new Integer(100);
        System.out.println(intObj2);
        changeValue(intObj2);
        System.out.println(intObj2);

        int intPrimitive2 = 200;
        System.out.println(intPrimitive2);
        changeValue(intPrimitive2);
        System.out.println(intPrimitive2);


    }

    private static void changeValue(int intObj) {
        intObj = 100;
    }

    private static void changeValue2(Integer intObj) {
        intObj = 1000;
    }
}
