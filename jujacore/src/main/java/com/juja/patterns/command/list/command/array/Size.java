package com.juja.patterns.command.list.command.array;

import com.juja.patterns.command.list.ArrayReceiver;
import com.juja.patterns.command.list.Command;

public class Size implements Command {
    private ArrayReceiver receiver;

    public Size(ArrayReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Object execute(Object... args) {
        if (args.length != 0) {
            throw new IllegalArgumentException("неверный формат, ожидается вызов без параметров");
        }
        return receiver.size();
    }
}
