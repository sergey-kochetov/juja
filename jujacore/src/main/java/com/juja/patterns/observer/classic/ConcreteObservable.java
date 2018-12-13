package com.juja.patterns.observer.classic;

import java.util.Collection;
import java.util.LinkedList;

public class ConcreteObservable implements Observable {

    private Collection<Observer> observers = new LinkedList<>();


    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Object data) {
        for (Observer observer : observers) {
            observer.handleEvent(data);
        }
    }
}
