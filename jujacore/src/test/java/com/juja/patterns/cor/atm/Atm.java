package com.juja.patterns.cor.atm;

import java.util.Arrays;

public class Atm {

    private MoneyStack first;

    public Atm() {
        MoneyStack stack100 = new MoneyStack(100);
        MoneyStack stack50 = new MoneyStack(50);
        MoneyStack stack20 = new MoneyStack(20);
        MoneyStack stack10 = new MoneyStack(10);
        MoneyStack stack5 = new MoneyStack(5);
        MoneyStack stack1 = new MoneyStack(1);

        stack100.setNextStack(stack50);
        stack50.setNextStack(stack20);
        stack20.setNextStack(stack10);
        stack10.setNextStack(stack5);
        stack5.setNextStack(stack1);

        first = stack100;
    }

    public Atm(int... billsSize) {
        Arrays.sort(billsSize);
        for (int size : billsSize) {
            MoneyStack node = new MoneyStack(size);
            if (first != null) {
                node.setNextStack(first);
            }
            first = node;
        }
    }

    public void withdraw(int amount) {
        System.out.println(amount + " =");
        first.withdraw(amount);
        System.out.println("++++++++++++++");
    }
}
