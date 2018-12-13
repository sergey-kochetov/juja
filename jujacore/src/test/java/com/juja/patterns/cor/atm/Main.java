package com.juja.patterns.cor.atm;

public class Main {

    public static void main(String[] args) {
        // Конструктор по умолчанию для номиналов 1, 5, 10, 20, 50 и 100
        Atm atm = new Atm();
        atm.withdraw(186);
        atm.withdraw(72);
        atm.withdraw(1564);

        Atm atm2 = new Atm(1, 2, 3, 5, 10, 15, 25,
                50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000);

        atm2.withdraw(1231241);
    }
}
