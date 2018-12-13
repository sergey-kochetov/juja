package com.juja.patterns.observer.classic;

public class Main {

    public static void main(String[] args) {
        Observable observable = new ConcreteObservable();

        Observer observer1 = new ConcreteObserverA();
        Observer observer2 = new ConcreteObserverA();
        Observer observer3 = new ConcreteObserverB();

        observable.addObserver(observer1);
        observable.addObserver(observer2);
        observable.addObserver(observer3);

        observable.notifyObservers("Hello Observer");

        observable.removeObserver(observer2);

        observable.notifyObservers("Hello again");
    }
}
