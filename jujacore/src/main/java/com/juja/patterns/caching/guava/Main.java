package com.juja.patterns.caching.guava;

import com.google.common.cache.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws ExecutionException {
        // строим наш кеш с помощьб Google Guava
        LoadingCache<Integer, Resource> resources =
                CacheBuilder.newBuilder()
                // вместимость кеша
                .maximumSize(4)
                // через 10 сек после создания ресурс будет помечен на удаление
                .expireAfterWrite(10, TimeUnit.SECONDS)
                // когда он будет удален то будет вызываться этот калбэк
                .removalListener((RemovalListener) (r) -> {
                    System.out.println("Removing " + r.getValue());
                })
                .build(new CacheLoader<Integer, Resource>() {
                    @Override
                    public Resource load(Integer key) throws Exception {
                        return loadResource(key);
                    }
                });

        // вывод
        System.out.println("Get " + resources.get(10));
        // Created Resource:10
        // Get Resource:10
        System.out.println("Get " + resources.get(10));
        // Get Resource:10
        System.out.println("Get " + resources.get(11));
        // Created Resource:11
        // Get Resource:11
        System.out.println("Get " + resources.get(12));
        // Created Resource:12
        // Get Resource:12
        System.out.println("Get " + resources.get(10));
        // Get Resource:10
        System.out.println("Get " + resources.get(13));
        // Created Resource:13
        // Get Resource:13
        System.out.println("Get " + resources.get(14));
        // Created Resource:14
        // Removing Resource:11
        // Get Resource:14
        System.out.println("Get " + resources.get(11));
        // Created Resource:11
        // Removing Resource:12
        // Get Resource:11

    }

    private static Resource loadResource(int id) {
        sleep(1000);
        Resource resource = new Resource(id);
        System.out.println("Created " + resource);
        return resource;
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // do nothing
        }
    }
}
