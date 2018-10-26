package com.juja.oop;

public class Liner extends AbstractShip {

    private int passengers;
    public static final float DEFAULT_RENTAL = 1000;

    public Liner(String name, float length, float width, float displacement) {
        super(name, length, width, displacement);
    }

    public Liner(String name, float length, float width, float displacement, int passengers) {
        super(name, length, width, displacement);
        this.passengers = passengers;
    }

    @Override
    public float calculatePayment() {

        return passengers * DEFAULT_RENTAL;
    }



    public float calculatePayment(float payment) {
        float result;
        if (payment > 0) {
            result = passengers * payment;
        } else {
            result = calculatePayment();
        }
        return result;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }
}


