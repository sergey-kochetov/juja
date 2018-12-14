package com.juja.patterns.command.list;

import com.juja.patterns.command.list.command.array.*;


public class MainArray {

    public static void main(String[] args) {
        ArrayReceiver array = new ArrayReceiver();

        Command add = new Add(array);
        Command toString = new ToString(array);
        Command size = new Size(array);
        Command isEmpty = new IsEmpty(array);
        Command get = new Get(array);
        Command remove = new Remove(array);

        // наш выполнятор
        Invoker invoker = new Invoker();




        invoker.setCommand(isEmpty).doit();
        invoker.setCommand(add).doit("one");
        invoker.setCommand(add).doit("two");
        invoker.setCommand(add).doit("three");
        invoker.setCommand(toString).doit();
        invoker.setCommand(size).doit();
        invoker.setCommand(isEmpty).doit();
        invoker.setCommand(get).doit(2);
        invoker.setCommand(remove).doit(0);
        invoker.setCommand(remove).doit(1);
        invoker.setCommand(toString).doit();
    }
}
