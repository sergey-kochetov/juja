package com.juja.patterns.state.classic.case1_extends;

public class Context {
    // храним состояние объекта
    private State state;

    // устанавливаем состояние по умолчанию
    public Context() {
        setState(new ConcreteStateA());
    }

    // этот запрос хочет выполнить клиент
    public void request(String data) {
        // ...
        state.handle(this, data);
        // ...
    }

    // скрытый сеттер(packaging)
    // поскольку конкретное состояние может изменить состояние
    // объекта Context но никто за приделами пакета не может
    void setState(State state) {
        String name = state.getClass().getSimpleName();
        System.out.println("Set state: " + name.charAt(name.length() - 1));
        this.state = state;
    }
}
