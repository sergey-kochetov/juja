package com.juja.patterns.caching.case1_simple;

import java.util.HashMap;
import java.util.Map;

// Наш ресурс с встроиным анлим-кешем
// пример нарушения SRP
public class Cache {
    // in memory - храним
    private Map<Integer, Resource> data = new HashMap<>();
    private Provider provider;

    public Cache(Provider provider) {
        this.provider = provider;
    }
    // главный метод получения ресурсов
    public Resource get(int id) {
        Resource resource = data.get(id);
        if (resource != null) {
            // если есть ресурс - то возвращаем
            return resource;
        }
        // в кеше ресурса нет, берем его более долгим способом
        resource = provider.get(id);
        if (resource != null) {
            // сохраняем в кеше
            data.put(id, resource);
        }
        return resource;
    }
}
