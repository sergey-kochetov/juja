package com.juja.patterns.command.classic;

// наш выполнятор
public class Invoker {
    // хранит команду как абстракцию
    private Command command;
    // и с ней он агрегат - то есть ее можно заменить на runtime
    public void setCommand(Command command) {
        this.command = command;
    }
    // а это еденственный метод который будет дергать клиент
    public void doit() {
        System.out.println(command.execute("data"));
    }
}
