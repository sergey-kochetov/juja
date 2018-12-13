package com.juja.patterns.strategy;

public interface Lock {

    public void unlock(Key key);

    boolean isOpened();
}
