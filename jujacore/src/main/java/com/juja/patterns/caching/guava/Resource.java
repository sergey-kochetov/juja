package com.juja.patterns.caching.guava;

public class Resource {
    private final int key;
    public Resource(int key) {
        this.key = key;
    }

    public int get() {
        return key;
    }

    @Override
    public String toString() {
        return "Resource:" + key;
    }

}
