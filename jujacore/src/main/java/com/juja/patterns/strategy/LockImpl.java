package com.juja.patterns.strategy;

public class LockImpl implements  Lock {

    private boolean opened = true;
    private String secret;

    public LockImpl(String secret) {
        this.secret = secret;

    }

    @Override
    public void unlock(Key key) {
        if (key.getCode().equals(secret)) {
            opened = !opened;
        }

    }

    @Override
    public boolean isOpened() {
        return opened;
    }
}
