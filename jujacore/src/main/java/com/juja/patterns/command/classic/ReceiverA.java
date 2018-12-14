package com.juja.patterns.command.classic;

public class ReceiverA {
    public Object method1(Object input) {
        return "Processed by ReceiverA in method 1: " + input;
    }

    public Object method2(Object input) {
        return "Processed by ReceiverA in method 2: " + input;
    }
}
