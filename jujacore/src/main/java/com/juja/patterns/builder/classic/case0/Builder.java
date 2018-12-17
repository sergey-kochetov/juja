package com.juja.patterns.builder.classic.case0;

public abstract class Builder {
    // тут храним собираемый продукт
    protected Product product;

    public void buildPart1() {
        product = new Product();
    }
    public Product getResult() {
        return product;
    }

    protected abstract void buildPart2();
    protected abstract void buildPart3();
    protected abstract void buildPart4();
}
