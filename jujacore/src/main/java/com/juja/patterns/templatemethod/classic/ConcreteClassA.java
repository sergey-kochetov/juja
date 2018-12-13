package com.juja.patterns.templatemethod.classic;

public class ConcreteClassA extends AbstractClass {

    // Всеже решили его переделать
    @Override
    protected Object algorithm1() {
        return "algorithmA";
    }

    @Override
    protected void algorithm2(Object input) {
        System.out.println("algorithm2A prints " + input.toString());
    }

    @Override
    protected void algorithm3() {
        System.out.println("algorithm3A prints smth");
    }

    @Override
    protected Object algorithm4(Object input) {
        return String.format("algorithm4A %s", input.toString());
    }
}
