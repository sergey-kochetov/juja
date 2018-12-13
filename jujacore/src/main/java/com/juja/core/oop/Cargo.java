package com.juja.core.oop;

public class Cargo extends AbstractShip {
    private float tonnage;
    public static final float DEFAULT_RENTAL=550;

    public Cargo(String name, float length, float width, float displacement) {
        super(name, length, width, displacement);
    }

    public Cargo(String name, float length, float width, float displacement, float tonnage) {
        super(name, length, width, displacement);
        this.tonnage = tonnage;
    }

    @Override
    public float calculatePayment() {
        return DEFAULT_RENTAL * tonnage;
    }

    public float calculatePayment(float money) {
        if (money > 0) {
            return money * tonnage;
        }
        return calculatePayment();
    }



}