package com.juja.patterns.observer.classic;

public class ConcreteObserverA implements Observer {

    @Override
    public void handleEvent(Object input) {
        System.out.println("ConcreteObserverA:" + input);
    }
}
