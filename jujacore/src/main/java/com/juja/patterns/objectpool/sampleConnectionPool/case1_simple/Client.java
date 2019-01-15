package com.juja.patterns.objectpool.sampleConnectionPool.case1_simple;

public class Client {
    private final ObjectPool pool;

    public Client(ObjectPool pool) {
        this.pool = pool;
    }
    // полезный метод
    public void doSomething() {
        //... код ...
        Resource resource1 = pool.get();
        //... код ...
        Resource resource2 = pool.get();
        //... код ...
        resource1.doit("data1");
        pool.put(resource1);
        //... код ...
        Resource resource3 = pool.get();
        resource3.doit("data3");
        pool.put(resource3);
        //... код ...
        resource2.doit("data2");
        pool.put(resource2);
    }
}
