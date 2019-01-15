package com.juja.patterns.objectpool.sampleConnectionPool.case1_simple;

import java.util.LinkedList;
import java.util.Queue;

public class ObjectPool {
    private Queue<Resource> poll = new LinkedList<>();

    //  в конструкторе указывается, максимальное количество ресурсов
    public ObjectPool(int maxCount) {
        System.out.println("init pool...");
        for (int index = 0; index < maxCount; index++) {
            put(new Resource());
        }
        System.out.println("...init done!");
    }

    // возвращаем ресурс на место
    public void put(Resource resource) {
        if (!poll.contains(resource)) {
            System.out.println("put " + resource + " to pool");
            poll.add(resource);
        }
    }

    public Resource get() {
        Resource resource = poll.poll();
        if (resource == null) {
            throw new RuntimeException("pool is empty!");
        }
        System.out.println("get " + resource + " from pool");
        return resource;
    }
}
