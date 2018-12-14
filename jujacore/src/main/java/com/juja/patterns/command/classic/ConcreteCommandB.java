package com.juja.patterns.command.classic;

// вторая комманда
public class ConcreteCommandB implements Command {
    private ReceiverB receiver;

    //тотже композит с ReceiverB
    public ConcreteCommandB(ReceiverB receiver) {
        this.receiver = receiver;
    }

    @Override
    //делает свою логику
    public Object execute(Object input) {
        receiver.otherMethod(input);
        return "CommandB result";
    }
}
