package com.juja.patterns.objectpool.sampleConnectionPool.case4_withProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by oleksandr.baglai on 28.01.2016.
 */
// это наш пул ресурсов
// (тут все так же как в примере case1_simple.ObjectPool
//      за исключением строк, помеченных как /*!!*/ )
public class ObjectPool {

    // он хранит список ресурсов, которые создавались заранее
    private Queue<Resource> pool = new LinkedList<Resource>();

    // в кострукторе указывается, максимальное количество создаваемых ресурсов
    public ObjectPool(int maxCount) {
        System.out.println("init pool...");

        for (int index = 0; index < maxCount; index++) {
            // ресурсы создаются и сразу же сохраняются
            // оборачиваясь в прокси, то есть мы не вовзращаем сам ресурс,
            // но то во что его обернули
            put(proxy(new ResourceImpl())); /*!!*/
        }

        System.out.println("...init done!");
    }

    // метод для оборачивания ресурса в прокси
    // прокси реализует тот же интерфейс что и ресурс
    // но на его метод close и в случае возникновения исплючений
    // он возвращает пару (прокси + ресурс) в пул
    private Resource proxy(final Resource resource) { /*!!*/
        return (Resource) Proxy.newProxyInstance(
                Resource.class.getClassLoader(),
                new Class[] {Resource.class},
                new InvocationHandler() {
                    // этот метод будет вызываться при вызове любого метода прокси
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // если это метод close, тогда ресурс надо вернуть на место
                        if (method.getName().equals("close")) {
                            ObjectPool.this.put((Resource) proxy);
                            return null;
                        }
                        // иначе делаем ппоытку вызова реального метода на ресурсе
                        try {
                            return method.invoke(resource, args);
                        } catch (Exception e) {
                            // а в случае ошибки, например, можем вернуть ресурс на место
                            // ObjectPool.this.put((Resource) proxy);
                            // или пусть клиент сам позаботится об этом
                            throw e;
                        }
                    }
                });
    }

    // этим методом мы извлекаем ресурсы для работы с ними
    public Resource get() {
        Resource resource = pool.poll();
        // но если вдруг ресурсов не хватает - тогда беда :)
        // (это только для этого семпла беда - в другом мы выкрутимся!)
        if (resource == null) {
            throw new RuntimeException("pool is empty!");
        }

        System.out.println("get " + resource +  " from pool");
        return resource;
    }

    // этим методом мы возвращаем ресурс на место (если его там нет),
    // чтобы его можно было повторно использовать
    // метод заметь закрытый, чтобы вернуть ресурс на место надо вызвать
    // resource.close();
    private void put(Resource resource) {
        if (!pool.contains(resource)) {
            System.out.println("put " + resource + " to pool");

            pool.add(resource);
        }
    }
}
