package com.juja.patterns.command.list.command.array;

import com.juja.patterns.command.list.ArrayReceiver;
import com.juja.patterns.command.list.Command;

public class IsEmpty implements Command {
    private ArrayReceiver receiver;

    public IsEmpty(ArrayReceiver receiver) {
        this.receiver = receiver;
    }
    @Override
    public Object execute(Object... args) {
        return receiver.size() == 0 ? "Да" : "Нет";
    }
}
