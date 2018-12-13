package com.juja.patterns.observer.classic;

public interface Observable {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers(Object data);
}
