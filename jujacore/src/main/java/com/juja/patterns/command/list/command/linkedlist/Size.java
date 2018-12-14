package com.juja.patterns.command.list.command.linkedlist;

import com.juja.patterns.command.list.Command;
import com.juja.patterns.command.list.LinkedListReceiver;

public class Size implements Command {
    private final LinkedListReceiver reciever;

    public Size(LinkedListReceiver array) {
        this.reciever = array;
    }

    @Override
    public Object execute(Object... args) {
        if (args.length != 0) {
            throw new IllegalArgumentException("неверный формат, ожидается вызов без параметров");
        }
        int result = 0;
        LinkedListReceiver.Node node = reciever.getFirst();
        while (node != null) {
            result++;
            node = node.next;
        }
        return result;
    }
}
