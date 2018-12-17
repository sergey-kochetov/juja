package com.juja.patterns.nullObject.classic;

public class Main {
    public static void main(String[] args) {

        Abstraction abstraction = new ConcreteObject();

        Client1 client1 = new Client1(abstraction);
        client1.doSomething("data");

        printBreak();

        Client2 client2 = new Client2();
        client2.doSomething(abstraction);

        printBreak();
    }

    private static void printBreak() {
        System.out.println("--------------------");
    }
}
