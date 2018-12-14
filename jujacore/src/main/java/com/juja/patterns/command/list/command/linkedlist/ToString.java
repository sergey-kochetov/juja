package com.juja.patterns.command.list.command.linkedlist;

import com.juja.patterns.command.list.Command;
import com.juja.patterns.command.list.LinkedListReceiver;

public class ToString implements Command {
    private final LinkedListReceiver reciever;

    public ToString(LinkedListReceiver array) {
        this.reciever = array;
    }

    @Override
    public Object execute(Object... args) {
        if (args.length != 0) {
            throw new IllegalArgumentException("неверный формат, ожидается вызов без параметров");
        }
        StringBuffer buffer = new StringBuffer("[");
        LinkedListReceiver.Node node = reciever.getFirst();
        while (node != null) {
            buffer.append(node.value);
            node = node.next;
            if (node != null) {
                buffer.append(", ");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }
}
