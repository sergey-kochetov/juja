package com.juja.patterns.observer.classic;

public class ConcreteObserverB implements Observer {

    @Override
    public void handleEvent(Object input) {
        System.out.println("ConcreteObserverB:" + input);
    }
}
