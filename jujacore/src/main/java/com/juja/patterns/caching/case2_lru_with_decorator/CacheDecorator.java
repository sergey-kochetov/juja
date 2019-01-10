package com.juja.patterns.caching.case2_lru_with_decorator;

// легковесный кеш-декоратор
public class CacheDecorator implements Provider {
    // ... хранит
    private Provider provider;
    private LRUCache cache;

    public CacheDecorator(Provider provider, int maxSize) {
        this.provider = provider;
        this.cache = new LRUCache(maxSize);
    }

    @Override
    public Resource get(int key) {
        // пробуем взять из кеша если нет то от поставщика
        Resource resource = cache.get(key);
        if (resource == null) {
            resource = provider.get(key);
            if (resource != null) {
                cache.put(key, resource);
            }
        }
        return resource;
    }
}
