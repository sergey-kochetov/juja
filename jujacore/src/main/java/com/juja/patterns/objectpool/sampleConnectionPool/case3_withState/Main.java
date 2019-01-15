package com.juja.patterns.objectpool.sampleConnectionPool.case3_withState;

// класс-запускатор
public class Main {

    public static void main(String[] args) {
        // создаем пул ресурсов, в котором будет не больше 3х элементов
        // клиенту этог оне хватит!
        ObjectPool pool = new ObjectPool(2);

        // конфигурируем клиента пулом ресурсов
        Client client = new Client(pool);

        // выполняем полезную логику
        client.doSomething();

        // вывод:
        // init pool...
        // Resource[null]@1 state = null
        // put Resource[null]@1 to pool
        // Resource[null]@2 state = null
        // put Resource[null]@2 to pool
        // ...init done!
        // get Resource[null]@1 from pool
        // Resource[null]@1 state = state1
        // get Resource[null]@2 from pool
        // Resource[state1]@1 state = state2
        // Resource[state2]@1 processed: data1
        // Resource[state2]@1 state = null
        // put Resource[null]@1 to pool
        // get Resource[null]@1 from pool
        // Resource[null]@1 state = state3
        // Resource[state3]@1 processed: data3
        // Resource[state3]@1 state = null
        // put Resource[null]@1 to pool
        // Resource[null]@2 processed: data2
        // Resource[null]@2 state = null
        // put Resource[null]@2 to pool
    }

}
