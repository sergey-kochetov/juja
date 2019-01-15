package com.juja.patterns.objectpool.sampleConnectionPool.case6_apacheCommonsPoll;

import org.apache.commons.pool2.ObjectPool; /*!!*/

// наш клиент, симулирующий работу с тремся ресурсами через пул
// (тут все так же как в примере ua.com.juja.patterns.callback.classic.case1_simple.Client
//      за исключением строк, помеченных как /*!!*/)
public class Client {

    // сохраним пул в поле, для дальнейшего использования
    private ObjectPool<Resource> pool;

    // клиент конфигурируется пулом через конструктор
    public Client(ObjectPool<Resource> pool) {
        this.pool = pool;
    }

    // полезный метод
    public void doSomething() throws Exception {
            // тут какой-то другой код, который мог бы делать что-то очень полезное

        // взяли первый ресурс
        Resource resource1 = pool.borrowObject(); /*!!*/

            // какой-то другой код

        // взяли еще один ресурс (на потом)
        Resource resource2 = pool.borrowObject(); /*!!*/

            // какой-то другой код

        // отработали на первом и вернули его
        resource1.doit("data1");
        pool.returnObject(resource1); /*!!*/

            // какой-то другой код

        // взяли третий, отработали и вернули
        Resource resource3 = pool.borrowObject(); /*!!*/
        resource3.doit("data3");
        pool.returnObject(resource3); /*!!*/

            // какой-то другой код

        // отработали на втором
        resource2.doit("data2");

            // какой-то другой код

        // вернули второй
        pool.returnObject(resource2); /*!!*/

            // какой-то другой код
    }
}
