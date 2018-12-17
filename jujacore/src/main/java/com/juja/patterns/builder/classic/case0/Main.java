package com.juja.patterns.builder.classic.case0;

public class Main {
    public static void main(String[] args) {
        // создаем билдер и дирежора
        Builder builder = new ConcreteBuilder();
        Director director = new Director(builder);
        // собираем объект
        director.construct();
        // получем объект
        Product product = director.getProduct();

        System.out.println(product);
    }
}
