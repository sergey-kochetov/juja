package com.juja.patterns.command.list;

import java.util.Arrays;

public class Invoker {
    // хранит команду как абстракцию
    private Command command;
    // и с ней он агрегат - то есть ее можно заменить на runtime

    public Invoker setCommand(Command command) {
        this.command = command;
        return this;
    }
    // а это еденственный метод который будет дергать клиент
    public void doit(Object... args) {
        Object result = command.execute(args);

        System.out.printf("%s%s = %s\n",
                command.getClass().getSimpleName(),
                Arrays.toString(args), result);
    }
}
