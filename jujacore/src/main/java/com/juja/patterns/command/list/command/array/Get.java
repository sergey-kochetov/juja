package com.juja.patterns.command.list.command.array;

import com.juja.patterns.command.list.ArrayReceiver;
import com.juja.patterns.command.list.Command;

public class Get implements Command {
    private ArrayReceiver receiver;

    public Get(ArrayReceiver receiver) {
        this.receiver = receiver;
    }
    @Override
    public Object execute(Object... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("неверный формат, ожидается вызов c 1 параметром");
        }
        return receiver.get((Integer) args[0]);
    }
}
