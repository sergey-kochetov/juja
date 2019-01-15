package com.juja.patterns.objectpool.sampleConnectionPool.case6_apacheCommonsPoll;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

// класс-запускатор
public class Main {

    public static void main(String[] args) throws Exception {
        // эта фабрика инкапсулирует в себе логику создания ресурсов
        // анонимно дореализовыввем ее из BasePooledObjectFactory
        PooledObjectFactory<Resource> factory = new BasePooledObjectFactory<Resource>() {
            // это момент создания нашего объекта
            @Override
            public Resource create() throws Exception {
                return new Resource();
            }

            // это процесс оборачивания объекта в обертку для целей пула
            @Override
            public PooledObject<Resource> wrap(Resource resource) {
                return new DefaultPooledObject<Resource>(resource);
            }
        };

        // конфигурим пул, чтобы он содержал не более 2х элементов
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(2);
        // тут есть и другие методы
        // config.setMinIdle(...
        // config.setMaxIdle(...

        // создаем пул ресурсов
        ObjectPool<Resource> pool = new GenericObjectPool<Resource>(factory, config) {
            // вообще это переопределять не надо, для вывода в консоль это сделано
            @Override
            public void returnObject(Resource resource) {
                System.out.println("put " + resource + " to pool");

                super.returnObject(resource);
            }

            @Override
            public Resource borrowObject() throws Exception {
                Resource resource = super.borrowObject();

                System.out.println("get " + resource +  " from pool");

                return resource;
            }
        };

        // конфигурируем клиента пулом ресурсов
        Client client = new Client(pool);

        // выполняем полезную логику
        client.doSomething();

        // вывод:
        // get Resource@1 from pool       <---- берем первый ресурс
        // get Resource@2 from pool       <---- берем второй ресурс
        // Resource@1 processed: data1
        // put Resource@1 to pool
        // get Resource@1 from pool       <---- берем (снова) первый ресурс
        // Resource@1 processed: data3
        // put Resource@1 to pool
        // Resource@2 processed: data2
        // put Resource@2 to pool
    }

}
