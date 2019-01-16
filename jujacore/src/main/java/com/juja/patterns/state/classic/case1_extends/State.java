package com.juja.patterns.state.classic.case1_extends;

public abstract class State {

    public void handle(Context context, String data) {
        System.out.println("Handled by default: " + data);
    }
}
