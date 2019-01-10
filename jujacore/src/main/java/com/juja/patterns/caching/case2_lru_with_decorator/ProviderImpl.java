package com.juja.patterns.caching.case2_lru_with_decorator;

public class ProviderImpl implements Provider {
    @Override
    public Resource get(int key) {
        sleep(1000);
        Resource resource = new Resource(key);
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
