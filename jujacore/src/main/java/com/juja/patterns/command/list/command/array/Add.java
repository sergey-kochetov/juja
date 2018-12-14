package com.juja.patterns.command.list.command.array;

import com.juja.patterns.command.list.ArrayReceiver;
import com.juja.patterns.command.list.Command;

public class Add implements Command {
    private final ArrayReceiver reciever;

    public Add(ArrayReceiver array) {
        this.reciever = array;
    }

    @Override
    public Object execute(Object... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("неверный формат, ожидается вызов c 1 параметром");
        }
        reciever.copyOf(reciever.size() + 1);
        reciever.set(reciever.size() - 1, (String) args[0]);
        return reciever.size();
    }
}
