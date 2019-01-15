package com.juja.patterns.objectpool.sampleConnectionPool.case5_multithreading;

// наш клиент, симулирующий работу с тремся ресурсами через пул
public class Client {

    // сохраним пул в поле, для дальнейшего использования
    private ObjectPool pool;

    // клиент конфигурируется пулом через конструктор
    public Client(ObjectPool pool) {
        this.pool = pool;
    }

    // полезный метод
    public void doSomething(int count) {
        // допустим он делает что-то с ресурсом 'count' раз
        for (int index = 0; index < count; index++) {
            // это для того, чтобы случилось замыкание :)
            final int finalIndex = index;

            // запускаем новый поток, который
            new Thread(new Runnable() {
                @Override
                public void run() {
                        // тут какой-то другой код, который мог бы делать что-то очень полезное

                    // берем ресурс
                    Resource resource = pool.get();

                        // какой-то другой код

                    // делаем что-то полезное
                    resource.doit("data" + finalIndex);

                        // какой-то другой код

                    // возвращаем ресурс на место
                    pool.put(resource);

                        // какой-то другой код
                }
            }).start();
        }
    }
}
