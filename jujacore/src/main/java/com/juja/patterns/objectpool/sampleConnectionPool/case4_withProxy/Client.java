package com.juja.patterns.objectpool.sampleConnectionPool.case4_withProxy;

// наш клиент, симулирующий работу с тремя ресурсами через пул
// (тут все почти так же, как в примере classic.case1_simple.Client
//      за исключением строк помеченных как /*!!*/ )
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

        // взяли ресурсы
        Resource resource1 = pool.get(); /*!!*/
        Resource resource2 = pool.get(); /*!!*/
        Resource resource3 = pool.get(); /*!!*/
        try {
                // какой-то другой код

            // отработали на первом
            resource1.doit("data1");

                // какой-то другой код

            // отработали на третьем
            resource3.doit("data3");

                // какой-то другой код

            // отработали на втором
            resource2.doit("data2");

                // какой-то другой код
        } finally {
            // вернули все ресурсы
            resource1.close(); /*!!*/
            resource2.close(); /*!!*/
            resource3.close(); /*!!*/
        }
    }
}
