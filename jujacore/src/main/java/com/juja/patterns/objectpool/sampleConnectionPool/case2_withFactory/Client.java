package com.juja.patterns.objectpool.sampleConnectionPool.case2_withFactory;
// наш клиент, симулирующий работу с тремся ресурсами через пул
// (тут все так же как в примере classic.case1_simple.Client)
public class Client {
    // сохраним пул в поле, для дальнейшего использования
    private ObjectPool pool;

    // клиент конфигурируется пулом через конструктор
    public Client(ObjectPool pool) {
        this.pool = pool;
    }

    // полезный метод
    public void doSomething() {
        // тут какой-то другой код, который мог бы делать что-то очень полезное

        // взяли первый ресурс
        Resource resource1 = pool.get();

        // какой-то другой код

        // взяли еще один ресурс (на потом)
        Resource resource2 = pool.get();

        // какой-то другой код

        // отработали на первом и вернули его
        resource1.doit("data1");
        pool.put(resource1);

        // какой-то другой код

        // взяли третий, отработали и вернули
        Resource resource3 = pool.get();
        resource3.doit("data3");
        pool.put(resource3);

        // какой-то другой код

        // отработали на втором
        resource2.doit("data2");

        // какой-то другой код

        // вернули второй
        pool.put(resource2);

        // какой-то другой код
    }
}
