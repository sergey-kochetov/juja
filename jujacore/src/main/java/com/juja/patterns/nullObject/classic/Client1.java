package com.juja.patterns.nullObject.classic;

public class Client1 {
    Abstraction abstraction;

    public Client1(Abstraction abstraction) {
        this.abstraction = abstraction;
    }

    public void doSomething(String data) {
        System.out.println(data);
    }
}
