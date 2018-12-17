package com.juja.patterns.builder.classic.case1_doubleBrace;

public class Main {

    public static void main(String[] args) {
        Product object = new Product.Builder() {{
            field1 = "data1";
            field2 = "data2";
            field3 = "data3";
        }}.build();

        System.out.println(object);
    }
}
