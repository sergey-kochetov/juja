package com.juja.patterns.objectpool.sampleConnectionPool.case5_multithreading;

// класс-запускатор
public class Main {

    public static void main(String[] args) {
        // создаем пул ресурсов, в котором будет не больше 3х элементов
        ObjectPool pool = new ObjectPool(3);

        // конфигурируем клиента пулом ресурсов
        Client client = new Client(pool);

        // выполняем полезную логику для которой потребуется больше ресурсов
        client.doSomething(6);
    }
}
