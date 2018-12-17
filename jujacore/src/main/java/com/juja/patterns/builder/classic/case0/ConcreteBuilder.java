package com.juja.patterns.builder.classic.case0;

public class ConcreteBuilder extends Builder {
    @Override
    protected void buildPart2() {
        product.setField1("data1");
    }

    @Override
    protected void buildPart3() {
        product.setField2("data2");
    }

    @Override
    protected void buildPart4() {
        product.setField3("data3");
    }
}
