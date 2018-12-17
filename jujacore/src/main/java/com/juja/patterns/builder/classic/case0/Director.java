package com.juja.patterns.builder.classic.case0;
// класс управляющий очередностью этапов сборки
public class Director {
    // зависит от builder
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }
    // плон конструирования объекта
    public void construct() {
        builder.buildPart1();
        builder.buildPart2();
        builder.buildPart3();
        builder.buildPart4();
    }
    // метод для получения продускта
    public Product getProduct() {
        return builder.getResult();
    }
}
