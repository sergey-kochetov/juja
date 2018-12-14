package com.juja.patterns.command.classic;

public class ConcreteCommandC implements Command {
    private ReceiverA receiver;

    public ConcreteCommandC(ReceiverA receiverA) {
        this.receiver = receiverA;
    }

    @Override
    // тоже своя логика
    public Object execute(Object input) {
        return receiver.method2("Changed in Command: " + input);
    }
}
