package com.juja.patterns.command.classic;

public class ConcreteCommandA implements Command {
    private ReceiverA receiver;
    // работает с ReceiverA
    // затем это композит! комманда без Receiver ек может существовать
    // иначе может быть NPE в методе execute
    public ConcreteCommandA(ReceiverA receiver) {
        this.receiver = receiver;
    }
    @Override
    //  а тут логика того, что комманда делает со своим Receiver
    public Object execute(Object input) {
        return receiver.method1(input);
    }
}
