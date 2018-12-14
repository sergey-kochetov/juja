package com.juja.patterns.command.list.command.array;

import com.juja.patterns.command.list.ArrayReceiver;
import com.juja.patterns.command.list.Command;

public class ToString implements Command {
    private ArrayReceiver receiver;

    public ToString(ArrayReceiver receiver) {
        this.receiver = receiver;
    }
    @Override
    public Object execute(Object... args) {
        if (args.length != 0) {
            throw new IllegalArgumentException("неверный формат, ожидается вызов без параметров");
        }
        StringBuffer buffer = new StringBuffer("[");
        for (int i = 0; i < receiver.size(); i++) {
            buffer.append(receiver.get(i));
            if (i != receiver.size() - 1) {
                buffer.append(", ");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }
}
