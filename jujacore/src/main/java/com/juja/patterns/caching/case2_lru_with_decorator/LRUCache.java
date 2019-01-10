package com.juja.patterns.caching.case2_lru_with_decorator;

import com.juja.patterns.caching.case2_lru_with_decorator.Resource;

import java.util.LinkedHashMap;
import java.util.Map;

// реализация LRU(least recently used) кеша
// задаем размер на старте, при переполнении
// удаляем самый давно не используемый
public class LRUCache {
    private Map<Integer, Resource> data;
    public LRUCache(final int maxSize) {
        this.data = new LinkedHashMap(maxSize + 1, .75F, true) {
            @Override
            // этот метод будет вызван сразу после добавления нового
            public boolean removeEldestEntry(Map.Entry eldest) {
                boolean willRemove = size() > maxSize;
                if (willRemove) {
                    System.out.println("Removing" + eldest.getValue());
                }
                return willRemove;
            }
        };
//        LRUMap из Apache -> maven -> commons-collerction -> version 3.2.2
//        this.data = new LRUMap(maxSize) {
//            @Override
//            protected boolean removeLRU(LinkEntry eldest) {
//                System.out.println("Removing" + eldest.getValue());
//                return super.removeLRU(eldest);
//            }
//        };
    }

    public Resource get(int key) {
        return data.get(key);
    }

    public void put(int key, Resource resource) {
        data.put(key, resource);
    }

    public boolean contains(int key) {
        return data.containsKey(key);
    }
}
