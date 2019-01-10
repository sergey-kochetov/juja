package com.juja.patterns.caching.case2_lru_with_decorator;

// дает ресурсы по id
public interface Provider {
    Resource get(int key);
}
