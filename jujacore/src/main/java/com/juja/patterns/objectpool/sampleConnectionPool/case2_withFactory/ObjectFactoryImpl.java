package com.juja.patterns.objectpool.sampleConnectionPool.case2_withFactory;

// реализация фабричного метода
public class ObjectFactoryImpl implements ObjectFactory {
    @Override
    public Resource createResource() {
        // просто создает ресурс
        Resource resource = new Resource();

        System.out.println("create " + resource + " from factory-method");
        return resource;
    }
}
