package com.juja.patterns.objectpool.sampleConnectionPool.case5_multithreading;

import com.juja.patterns.objectpool.IdGenerator;

import java.util.Random;

// ресурс - это класс инскапсулирующий какую-то полезную,
// многократно используемую ответственность
public class Resource {

    // время на создание (или инициализацию) этого ресурса существенно
    public Resource() {
        setup();
    }

    // этот метод будет вызывать клиент у готового ресурса
    public void doit(String data) {
        calculate();
        System.out.println(this + " processed: " + data);
    }

    // метод для красивого вывода ресурса в консоль
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + id;
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

    // симуляция какой-то другой ресурсоемкой логики :)
    private void calculate() {
        try {
            Thread.sleep(new Random().nextInt(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
