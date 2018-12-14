package com.juja.patterns.command.classic;

public class Main {

    public static void main(String[] args) {
        // эти алгоритмы run-им из Invoker
        // но мы не хотим при этом, чтобы он про них знал
        ReceiverA receiverA = new ReceiverA();
        ReceiverB receiverB = new ReceiverB();
        //инкапсулируем по одному кейзу на 1 комманду
        Command commandA = new ConcreteCommandA(receiverA);
        Command commandB = new ConcreteCommandB(receiverB);
        Command commandC = new ConcreteCommandC(receiverA);
        // наш запускатор
        Invoker invoker = new Invoker();

        printBreak();
        // вставляем в него команду, под видом абстракции
        invoker.setCommand(commandA);
        // и выполяем
        // ...
        invoker.doit();
        printBreak();

        invoker.setCommand(commandB);
        invoker.doit();
        printBreak();

        invoker.setCommand(commandC);
        invoker.doit();
        printBreak();

        invoker.setCommand(commandA);
        invoker.doit();
        printBreak();


    }

    private static void printBreak() {
        System.out.println("+++++++++++++++++++");
    }
}
