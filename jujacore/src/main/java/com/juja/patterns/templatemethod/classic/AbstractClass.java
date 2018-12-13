package com.juja.patterns.templatemethod.classic;

public abstract class AbstractClass {
    // шаблонный метод в котором и есть общая идея метода
    public final Object templateMethod(Object input) {
        Object data1 = algorithm1();
        algorithm2(input);
        algorithm3();
        Object data2 = algorithm4(input);

        return String.format("%s plus %s", data1.toString(), data2.toString());
    }  
    // реализация алгоритма 1 по-умолчанию
    // наследники реализуют ее только, если захотят;
    protected Object algorithm1() { return "default_algorithm1";}
    // все последующие методы обязательны для реализакии
    protected abstract void algorithm2(Object input);

    protected abstract void algorithm3();

    protected abstract Object algorithm4(Object input);
}
