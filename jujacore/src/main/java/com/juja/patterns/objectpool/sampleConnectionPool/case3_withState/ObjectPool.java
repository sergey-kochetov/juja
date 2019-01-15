package com.juja.patterns.objectpool.sampleConnectionPool.case3_withState;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by oleksandr.baglai on 28.01.2016.
 */
// это наш пул ресурсов
// (тут все так же как в примере classic.case1_simple.ObjectPool
//      за исключением строк, помеченных как /*!!*/ )
public class ObjectPool {

    // он хранит список ресурсов, которые создавались заранее
    private Queue<Resource> pool = new LinkedList<Resource>();

    // в кострукторе указывается, максимальное количество создаваемых ресурсов
    public ObjectPool(int maxCount) {
        System.out.println("init pool...");

        for (int index = 0; index < maxCount; index++) {
            // ресурсы создаются и сразу же сохраняются
            put(new Resource());
        }

        System.out.println("...init done!");
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
    public void put(Resource resource) {
        // очищаем стейт! иначе утечка -
        // тот, кто его потом возьмет будет видеть старые данные
        resource.setState(null); /*!!*/

        if (!pool.contains(resource)) {
            System.out.println("put " + resource + " to pool");

            pool.add(resource);
        }
    }

}
