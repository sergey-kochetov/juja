package com.juja.patterns.caching.case1_simple;

// POJO - объект
public class Resource {
    private  int id;

    public Resource(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Resource:" + id;
    }
}
