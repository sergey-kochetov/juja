package com.juja.patterns.observer;

import com.juja.patterns.observer.classic.ConcreteObservable;
import com.juja.patterns.observer.classic.Observable;
import com.juja.patterns.observer.classic.Observer;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class ConcreteObservableTest {

    @Test
    public void testNotifyAll() {
        // given
        Observable observable = new ConcreteObservable();

        Observer observer1 = mock(Observer.class);
        Observer observer2 = mock(Observer.class);

        observable.addObserver(observer1);
        observable.addObserver(observer2);

        // when
        observable.notifyObservers("data");

        //then
        verify(observer1).handleEvent("data");
        verify(observer2).handleEvent("data");
    }

    @Test
    public void testNotifyOnlySubscribed() {
        // given
        Observable observable = new ConcreteObservable();

        Observer observer1 = mock(Observer.class);
        Observer observer2 = mock(Observer.class);

        observable.addObserver(observer1);
        observable.addObserver(observer2);

        // when
        observable.removeObserver(observer1);
        observable.notifyObservers("data");

        // then
        verifyNoMoreInteractions(observer1);
        verify(observer2).handleEvent("data");
    }

}