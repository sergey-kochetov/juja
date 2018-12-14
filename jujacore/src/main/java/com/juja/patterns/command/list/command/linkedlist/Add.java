package com.juja.patterns.command.list.command.linkedlist;

import com.juja.patterns.command.list.Command;
import com.juja.patterns.command.list.LinkedListReceiver;

public class Add implements Command {
    private final LinkedListReceiver reciever;

    public Add(LinkedListReceiver array) {
        this.reciever = array;
    }
    @Override
    public Object execute(Object... args) {
        if (args.length != 1 || !(args[0] instanceof String)) {
            throw new IllegalArgumentException("Ожидаем однин String");
        }
        String value = (String) args[0];
        LinkedListReceiver.Node last = reciever.getLast();
        if (last == null) {
            return ifIsEmpty(value);
        }
        return ifIsNotEmpty(value, last);
    }

    private Object ifIsNotEmpty(String value, LinkedListReceiver.Node last) {
        LinkedListReceiver.Node node = new LinkedListReceiver.Node();
        node.value = value;
        last.next = node;
        node.prev = last;
        reciever.setLast(node);
        return getSize();
    }

    private Object ifIsEmpty(String value) {
        LinkedListReceiver.Node node = new LinkedListReceiver.Node();
        node.value = value;
        reciever.setFirst(node);
        reciever.setLast(node);
        return 1;
    }

    private int getSize() {
        int result = 0;
        LinkedListReceiver.Node node = reciever.getFirst();
        while (node != null) {
            result++;
            node = node.next;
        }
        return result;
    }
}
