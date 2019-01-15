package com.juja.patterns.objectpool.sampleConnectionPool.case4_withProxy;

// класс-запускатор
public class Main {

    public static void main(String[] args) {
        // создаем пул ресурсов, в котором будет не больше 3х элементов
        // клиенту всего хватит!
        ObjectPool pool = new ObjectPool(3);

        // конфигурируем клиента пулом ресурсов
        Client client = new Client(pool);

        // выполняем полезную логику
        client.doSomething();

        // вывод:
        // init pool...
        // put ResourceImpl@1 to pool
        // put ResourceImpl@2 to pool
        // put ResourceImpl@3 to pool
        // ...init done!
        // get ResourceImpl@1 from pool
        // get ResourceImpl@2 from pool
        // get ResourceImpl@3 from pool
        // ResourceImpl@1 processed: data1
        // ResourceImpl@3 processed: data3
        // ResourceImpl@2 processed: data2
        // put ResourceImpl@1 to pool
        // put ResourceImpl@2 to pool
        // put ResourceImpl@3 to pool
    }

}
