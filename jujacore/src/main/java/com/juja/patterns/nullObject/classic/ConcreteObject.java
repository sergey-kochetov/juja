package com.juja.patterns.nullObject.classic;

public class ConcreteObject implements Abstraction {
    @Override
    public void request(String input) {
        System.out.println(input);
    }
}
