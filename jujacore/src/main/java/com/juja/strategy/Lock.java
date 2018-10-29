package com.juja.strategy;

public interface Lock {

    public void unlock(Key key);

    boolean isOpened();
}
