package com.juja.patterns.objectpool.sampleConnectionPool.case2_withFactory;

public interface ObjectFactory {
    // единственный метод для получения ресурса
    Resource createResource();
}
