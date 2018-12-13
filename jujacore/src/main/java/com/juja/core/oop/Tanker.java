package com.juja.core.oop;

public class Tanker extends AbstractShip {
    private float volume;
    public static final float DEFAULT_RENTAL = 5000;

    public Tanker(String name, float length, float width, float displacement) {
        super(name, length, width, displacement);
    }

    public Tanker(String name, float length, float width, float displacement, float volume) {
        super(name, length, width, displacement);
        this.volume = volume;
    }

    @Override
    public float calculatePayment() {
        return volume * DEFAULT_RENTAL;
    }
    public float calculatePayment(float renta) {
        if (volume < 0) {
           return volume * renta;
        } else {
            return calculatePayment();
        }

    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public static void main(String[] args) {
        String testTankerName = "TestTankerName";
        float testTankerLength = 1000;
        float testTankerWidth = 1000;
        float testTankerDisplacement = 1000;
        float testTankerVolume = 100;

        float testRentTaxNegative = -1;

        float expectedTankerPaymentRentTaxDefault = testTankerVolume * Tanker.DEFAULT_RENTAL;

        Tanker tanker = new Tanker(testTankerName, testTankerLength, testTankerWidth, testTankerDisplacement, testTankerVolume);

        //check payment if rent tax is negative
        float actualTankerPaymentRentTaxNegative = tanker.calculatePayment(testRentTaxNegative);
        if (actualTankerPaymentRentTaxNegative != expectedTankerPaymentRentTaxDefault)
            throw new AssertionError("Rent tax is negative= " + testRentTaxNegative + " and expected payment value= " + expectedTankerPaymentRentTaxDefault + " but found " + actualTankerPaymentRentTaxNegative);


        System.out.print("OK");

        String testTankerName2 = "TestTankerName";
        float testTankerLength2 = 1000;
        float testTankerWidth2 = 1000;
        float testTankerDisplacement2 = 1000;
        float testTankerVolume2 = 100;

        float testRentTax = 5000;
        float expectedTankerPaymentRentTaxNotDefault = testTankerVolume2 * testRentTax;

        Tanker tanker2 = new Tanker(testTankerName2, testTankerLength2, testTankerWidth2, testTankerDisplacement2, testTankerVolume2);

        //check payment if rent tax is not default
        float actualTankerPaymentRentTaxNotDefault = tanker.calculatePayment(testRentTax);
        if (actualTankerPaymentRentTaxNotDefault != expectedTankerPaymentRentTaxNotDefault)
            throw new AssertionError("Rent tax is not default= " + testRentTax + " and expected payment value= " + expectedTankerPaymentRentTaxNotDefault + " but found " + actualTankerPaymentRentTaxNotDefault);

        System.out.print("OK");
    }
}
