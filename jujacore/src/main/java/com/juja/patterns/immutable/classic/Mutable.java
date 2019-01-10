package com.juja.patterns.immutable.classic;

import java.util.List;

// изменяемы объект
public class Mutable {
    // лазейка - можно занаследоваться и влиять на поле
    protected List<String> data;
    // лазейка - можно передавать любой свой List и менять его потом
    public Mutable(List<String> data) {
        this.data = data;
    }
    // лазейка - в копирующем конструкторе - теперь два объекта будут
    // содержать ссылку на один и тот же список
    public Mutable(Mutable input) {
        this.data = input.data;
    }
    // геттер отдал оригинальный список
    // mutable.getData().clear() - инкапсуляция нарушена
    public List<String> getData() {
        return data;
    }
    // лазейка - сеттер ставит в поле сразу значение и клиент
    // может потом оставить себе ссылочку и менять
    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
