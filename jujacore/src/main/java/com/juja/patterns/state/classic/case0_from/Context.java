package com.juja.patterns.state.classic.case0_from;

public class Context {
    private int state;

    public Context() {
        state = 0;
    }

    // то что выполняет клиент
    public void request(String data) {
        if (state == 0) {
            System.out.println("Handled by A: " + data);
            state = 1;
        } else if (state == 1) {
            System.out.println("Handled by B: " + data);
            state = 2;
        } else if (state == 2) {
            System.out.println("Handled by C: " + data);
            state = 0;
        }
    }
}
