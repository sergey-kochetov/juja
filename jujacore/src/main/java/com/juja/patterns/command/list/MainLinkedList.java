package com.juja.patterns.command.list;

import com.juja.patterns.command.list.command.linkedlist.*;

public class MainLinkedList {

    public static void main(String[] args) {
        LinkedListReceiver linked = new LinkedListReceiver();

        Command add = new Add(linked);
        Command toString = new ToString(linked);
        Command size = new Size(linked);
        Command isEmpty = new IsEmpty(linked);
        Command get = new Get(linked);
        Command remove = new Remove(linked);

        // наш выполнятор
        Invoker invoker = new Invoker();




        invoker.setCommand(isEmpty).doit();
        invoker.setCommand(add).doit("one");
        invoker.setCommand(size).doit();
        invoker.setCommand(add).doit("two");
        invoker.setCommand(add).doit("three");
        invoker.setCommand(toString).doit();
        invoker.setCommand(size).doit();
        invoker.setCommand(isEmpty).doit();
        invoker.setCommand(get).doit(2);
        invoker.setCommand(remove).doit(0);
        invoker.setCommand(remove).doit(1);
        invoker.setCommand(toString).doit();
        invoker.setCommand(size).doit();
        invoker.setCommand(remove).doit(0);
        invoker.setCommand(size).doit();
    }
}
