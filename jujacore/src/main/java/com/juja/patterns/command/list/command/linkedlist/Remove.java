package com.juja.patterns.command.list.command.linkedlist;

import com.juja.patterns.command.list.Command;
import com.juja.patterns.command.list.LinkedListReceiver;

public class Remove implements Command {
    private final LinkedListReceiver reciever;

    public Remove(LinkedListReceiver array) {
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
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (node == reciever.getFirst()) {
            reciever.setFirst(node.next);
        }
        if (node == reciever.getLast()) {
            reciever.setLast(node.prev);
        }
        return node.value;
    }
}
