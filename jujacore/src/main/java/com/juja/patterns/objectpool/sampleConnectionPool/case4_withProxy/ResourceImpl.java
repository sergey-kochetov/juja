package com.juja.patterns.objectpool.sampleConnectionPool.case4_withProxy;

import com.juja.patterns.objectpool.IdGenerator;

// ресурс - это класс инскапсулирующий какую-то полезную,
// многократно используемую ответственность
// заметь тут мы использовали интерфейс и реализацию
public class ResourceImpl implements Resource {

    // время на создание (или инициализацию) этого ресурса существенно
    public ResourceImpl() {
        setup();
    }

    // этот метод будет вызывать клиент у готового ресурса
    @Override
    public void doit(String data) {
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

    // сам ресурс ничего не делает в этом методе
    @Override
    public void close() {
        // или может например чистить стейт ресурса, если бы он был
    }
}
