package com.juja.patterns.command.list.command.array;

import com.juja.patterns.command.list.ArrayReceiver;
import com.juja.patterns.command.list.Command;

public class Remove implements Command {
    private ArrayReceiver receiver;

    public Remove(ArrayReceiver receiver) {
        this.receiver = receiver;
    }
    @Override
    public Object execute(Object... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("неверный формат, ожидается вызов c 1 параметром");
        }
        int index = (int) args[0];
        if (index < 0 || index > receiver.size()) {
            return "Нечего удалять";
        }
        Object result = receiver.get(index);
        for (int i = index; i < receiver.size() - 1; i++) {
            receiver.set(i, receiver.get(i + 1));
        }
        receiver.copyOf(receiver.size() - 1);
        return result;
    }
}
