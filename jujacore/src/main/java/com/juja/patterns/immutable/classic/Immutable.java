package com.juja.patterns.immutable.classic;

import java.util.ArrayList;
import java.util.List;

// неизменяемый объект
// final класс, чтобы нельзя было занаследоваться
// и получить доступ к полям из наследника
public final class Immutable {
    // инкапсулируемые данные
    // обязательно private, чтобы доступа из вне небыло
    // и final, чтобы случайно не заменить внутри этого класса
    private final List<String> data;

    // конструктор с установкой поля
    // копирует данные передаваемые извне
    public Immutable(List<String> data) {
        this.data =  new ArrayList<>(data);
    }

    // копирует конструктор если он есть,
    // должен копировать данные перед уустановкой
    public Immutable(Immutable input) {
        this.data = new ArrayList<>(input.data);
    }

    // кроме того ссылка this не должна пропадать во время конструирования

    // геттеры должны отдавать копию
    public List<String> getData() {
        return new ArrayList<>(data);
    }
    // сеттеров нет, только через конструктор


    @Override
    public String toString() {
        return data.toString();
    }
}
