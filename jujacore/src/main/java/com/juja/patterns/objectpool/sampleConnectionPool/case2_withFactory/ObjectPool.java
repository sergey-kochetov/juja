package com.juja.patterns.objectpool.sampleConnectionPool.case2_withFactory;

import java.util.LinkedList;
import java.util.Queue;

// это наш пул ресурсов
// (тут все так же как в примере classic.case1_simple.ObjectPool
//      за исключением строк, помеченных как /*!!*/)
public class ObjectPool {

    // он хранит список ресурсов, которые создавались заранее
    private Queue<Resource> pool = new LinkedList<Resource>();

    // сохранили нашу фабрику, потом из нее извлекать будем ресурсы
    private ObjectFactory factory; /*!!*/

    // в кострукторе указывается, максимальное количество создаваемых ресурсов
    public ObjectPool(ObjectFactory factory) {
        // тут если ты заметил мы ничего не делаем
        // инициализировать ресурсы из фектори будем позже (lazy initialization)
        this.factory = factory; /*!!*/
    }

    // этим методом мы извлекаем ресурсы для работы с ними
    public Resource get() {
        Resource resource = pool.poll();
        // но если вдруг ресурсов не хватает - тогда дергаем за фектори!
        if (resource == null) {
            resource = factory.createResource(); /*!!*/
        }

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
