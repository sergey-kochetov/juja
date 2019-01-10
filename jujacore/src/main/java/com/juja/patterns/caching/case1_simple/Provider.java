package com.juja.patterns.caching.case1_simple;

public class Provider {

    public Resource get(int id) {
        sleep(1000);
        Resource resource = new Resource(id);
        System.out.println("Created " + resource);
        return resource;
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // do nothing
        }
    }
}
