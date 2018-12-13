package com.juja.patterns.templatemethod.classic;

public class Main {

    public static void main(String[] args) {
        String input = "<txt>";

        AbstractClass case1 = new ConcreteClassA();
        System.out.printf("result is: %s%n", case1.templateMethod(input));

        System.out.println("+++++++++++++");

        AbstractClass case2 = new ConcreteClassB();
        System.out.printf("result is: %s%n", case2.templateMethod(input));
    }
}
