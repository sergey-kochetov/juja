package com.juja.patterns.state.classic.case2_implements;

public class Context {

    // храним набор предустановленных состояний
    final State STATE_A = new ConcreteStateA(this);
    final State STATE_B = new ConcreteStateB(this);
    final State STATE_C = new ConcreteStateC(this);

    // храним рабочее состояние объекта
    State state;

    // устанавливаем состояние по умолчанию
    public Context() {
        setState(STATE_A);
    }

    // этот запрос хочет клиент выполнить
    // заметь тут контекст уже не передается вглубь
    // он уже там установлен во время создания экземпляра состояния
    public void request1(String data) {
        state.handle1(data);
    }

    // и этот запрос клиент тоже хочет выполнить
    public String request2() {
        return state.handle2();
    }

    // скрытый (видимый только в пакете) сеттер,
    // поскольку конкретное состояние может изменить состояние
    // объекта Context но никто за пределами пакета
    // не может этого делать
    void setState(State state) {
        String name = state.getClass().getSimpleName();
        System.out.println("Set state: " + name.charAt(name.length() - 1));
        this.state = state;
    }
}
