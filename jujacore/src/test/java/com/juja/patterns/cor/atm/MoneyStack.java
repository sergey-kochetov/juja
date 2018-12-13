package com.juja.patterns.cor.atm;

public class MoneyStack {

    private MoneyStack next;
    private final int billSize;

    public MoneyStack(int billSize) {
        this.billSize = billSize;
    }

    public void setNextStack(MoneyStack next) {
        this.next = next;
    }

    // алгорим размена
    public void withdraw(int amount) {
        // сколько целых купюр получается
        int numOfBills = Math.round(amount / billSize);

        if (numOfBills > 0) {
            ejectMoney(numOfBills);
            amount -= billSize * numOfBills;
        }

        if (amount > 0 && next != null) {
            next.withdraw(amount);
        }
    }

    private void ejectMoney(int numOfBills) {
        System.out.println(numOfBills + " x $" + billSize);
    }
}
