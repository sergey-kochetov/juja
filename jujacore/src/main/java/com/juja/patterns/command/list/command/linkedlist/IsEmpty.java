package com.juja.patterns.command.list.command.linkedlist;

import com.juja.patterns.command.list.Command;
import com.juja.patterns.command.list.LinkedListReceiver;

public class IsEmpty implements Command {
    private final LinkedListReceiver reciever;

    public IsEmpty(LinkedListReceiver array) {
        this.reciever = array;
    }
    @Override
    public Object execute(Object... args) {
        if (args.length != 0) {
            throw new IllegalArgumentException("неверный формат, ожидается вызов без параметров");
        }
        return reciever.getFirst() == null ? "Да" : "Нет";
    }
}
