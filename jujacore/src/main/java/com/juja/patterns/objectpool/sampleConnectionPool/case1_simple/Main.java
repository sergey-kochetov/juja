package com.juja.patterns.objectpool.sampleConnectionPool.case1_simple;

public class Main {
    public static void main(String[] args) {
        case1_enoughResources();

        printBreak();

        case2_notEnoughResources();
    }


    private static void printBreak() {
        System.out.println("-------------------------------");
    }

    private static void case1_enoughResources() {
        // создаем пул ресурсов, в котором будет не больше 3-х элементов
        // клиенту всего хватит!
        ObjectPool pool = new ObjectPool(3);

        // конфигурируем клиента пулом ркесурсов
        Client client = new Client(pool);

        // выполняем полезную логику
        client.doSomething();

    }

    private static void case2_notEnoughResources() {
        // создаем пул ресурсов, в котором будет не больше 2-х элементов
        // чего не достаточно для выполнения запросов клиента
        // а потому из ресурсов будет использовать дважды
        ObjectPool pool = new ObjectPool(2);

        // конфигурируем клиента пулом ресурсов
        Client client = new Client(pool);

        // выполняем полезную логику
        client.doSomething();
    }

}
