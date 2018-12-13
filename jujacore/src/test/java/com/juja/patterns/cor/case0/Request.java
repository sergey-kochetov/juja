package com.juja.patterns.cor.case0;

public class Request {

    private final Class aClass;
    private final Object message;

    public Request(Object message) {
        this.aClass = message.getClass();
        this.message = message;
    }

    public Object getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", aClass.getName(), message.toString());
    }
}
