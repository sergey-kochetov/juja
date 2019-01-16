package com.juja.patterns.state.classic.case1_extends;

public class ConcreteStateC extends State {
    @Override
    public void handle(Context context, String data) {
        System.out.println("Handled by A: " + data);
        context.setState(new ConcreteStateA());
    }
}
