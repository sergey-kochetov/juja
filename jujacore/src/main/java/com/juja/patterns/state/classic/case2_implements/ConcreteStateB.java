package com.juja.patterns.state.classic.case2_implements;

// состояние B
public class ConcreteStateB implements State {

    private Context context;

    public ConcreteStateB(Context context) {
        this.context = context;
    }

    @Override
    public void handle1(String data) {
        System.out.println("Handled by B: " + data);
        context.setState(context.STATE_C);
    }

    @Override
    public String handle2() {
        context.setState(context.STATE_A);
        return "Handled by B";
    }
}
