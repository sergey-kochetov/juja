package com.juja.patterns.state.classic.case2_implements;

// состояние C
public class ConcreteStateC implements State {

    private Context context;

    public ConcreteStateC(Context context) {
        this.context = context;
    }

    @Override
    public void handle1(String data) {
        System.out.println("Handled by C: " + data);
        context.setState(context.STATE_A);
    }


    @Override
    public String handle2() {
        context.setState(context.STATE_B);
        return "Handled by C";
    }
}
