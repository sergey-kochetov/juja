package com.juja.patterns.state.classic.case1_extends;

public class ConcreteStateA extends State {
    @Override
    public void handle(Context context, String data) {
        System.out.println("Handled by A: " + data);
        context.setState(new ConcreteStateB());
    }
}
