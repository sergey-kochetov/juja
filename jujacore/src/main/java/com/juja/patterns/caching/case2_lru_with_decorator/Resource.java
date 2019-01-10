package com.juja.patterns.caching.case2_lru_with_decorator;

public class Resource {
    private final int key;
    public Resource(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "Resource:" + key;
    }
}
