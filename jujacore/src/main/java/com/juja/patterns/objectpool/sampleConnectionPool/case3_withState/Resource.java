package com.juja.patterns.objectpool.sampleConnectionPool.case3_withState;

import com.juja.patterns.objectpool.IdGenerator;

// ресурс - это класс инскапсулирующий какую-то полезную,
// многократно используемую ответственность
// (тут все так же как в примере classic.case1_simple.Resource
//      за исключением строк, помеченных как /*!!*/ )
public class Resource {

    // состояние нашего ресурса
    private String state; /*!!*/

    // время на создание (или инициализацию) этого ресурса существенно
    public Resource() {
        setup();
    }

    // метод установки состояния
    public void setState(String state) {  /*!!*/
        System.out.println(this + " state = " + state);
        this.state = state;
    }

    // этот метод будет вызывать клиент у готового ресурса
    public void doit(String data) {
        System.out.println(this + " processed: " + data);
    }

    // метод для красивого вывода ресурса в консоль
    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "[" + state + "]" /*!!*/
                + "@" + id;
    }

    // это хак, не относящийся к шаблону -
    // сделано так, чтобы каждый новый ресурс имел свой порядковый номер
    public String id = IdGenerator.put(this);

    // симуляция какой-то ресурсоемкой логики
    private void setup() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
