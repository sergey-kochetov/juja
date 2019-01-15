package com.juja.patterns.objectpool.sampleConnectionPool.case5_multithreading;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

// это наш пул ресурсов
public class ObjectPool {

    // он хранит список ресурсов, которые создавались заранее
    // заметь, контейнер потоко-безопасный!
    private Queue<Resource> pool = new ConcurrentLinkedQueue<>();

    // в кострукторе указывается, максимальное количество создаваемых ресурсов
    public ObjectPool(int maxCount) {
        System.out.println("init pool...");

        for (int index = 0; index < maxCount; index++) {
            // ресурсы создаются и сразу же сохраняются
            put(createResource());
        }

        System.out.println("...init done!");
    }

    // в целях тестирования мы этот метод будем переопределять :)
    protected Resource createResource() {
        return new Resource();
    }

    // этим методом мы извлекаем ресурсы для работы с ними
    public Resource get() {
        Resource resource;

        // попытка (потокобезопасная) получить ресурс
        while ((resource = pool.poll()) == null) {
            // если его нет - выводим сообщение
            System.out.println("pool is empty - waiting for resource...");

            // и ждем немного
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // если получили, то все ок, возвращаем
        System.out.println("get " + resource +  " from pool");
        return resource;
    }

    // этим методом мы возвращаем ресурс на место (если его там нет),
    // чтобы его можно было повторно использовать
    public void put(Resource resource) {
        if (!pool.contains(resource)) {
            System.out.println("put " + resource + " to pool");

            pool.add(resource);
        }
    }
}
