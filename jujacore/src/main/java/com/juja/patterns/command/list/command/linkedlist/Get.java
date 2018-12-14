package com.juja.patterns.command.list.command.linkedlist;

import com.juja.patterns.command.list.Command;
import com.juja.patterns.command.list.LinkedListReceiver;

public class Get implements Command {
    private final LinkedListReceiver reciever;

    public Get(LinkedListReceiver array) {
        this.reciever = array;
    }

    @Override
    public Object execute(Object... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("неверный формат, ожидается вызов c 1 параметром");
        }
        int index = (int) args[0];
        int i = 0;
        LinkedListReceiver.Node node = reciever.getFirst();
        while (i != index) {
            i++;
            node = node.next;
        }
        return node.value;
    }
}
