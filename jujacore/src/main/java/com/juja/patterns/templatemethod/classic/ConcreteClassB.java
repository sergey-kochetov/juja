package com.juja.patterns.templatemethod.classic;

public class ConcreteClassB extends AbstractClass{
    @Override
    protected void algorithm2(Object input) {
        System.out.println("algorithm2B prints " + input.toString());
    }

    @Override
    protected void algorithm3() {
        System.out.println("algorithm3B prints smth");
    }

    @Override
    protected Object algorithm4(Object input) {
        return String.format("algorithm4B %s", input.toString());
    }
}
