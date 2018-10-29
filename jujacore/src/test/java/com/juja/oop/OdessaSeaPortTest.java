package com.juja.oop;

import org.junit.Test;

import static org.junit.Assert.*;

public class OdessaSeaPortTest {

    @Test
    public void test1() {
//check add into full queue
        String testLinerName = "TestLinerName";
        float testLinerLength = 1000;
        float testLinerWidth = 1000;
        float testLinerDisplacement = 1000;
        int testLinerPassengers = 100;
        String testCargoName = "TestCargoName";
        float testCargoLength = 1000;
        float testCargoWidth = 1000;
        float testCargoDisplacement = 1000;
        float testCargoTonnage = 100;
        String testTankerName = "TestTankerName";
        float testTankerLength = 1000;
        float testTankerWidth = 1000;
        float testTankerDisplacement = 1000;
        float testTankerVolume = 100;

        int expectedStatusAddShipIntoQueueFull = -1;

        AbstractShip testLiner = new Liner(testLinerName, testLinerLength, testLinerWidth, testLinerDisplacement, testLinerPassengers);
        AbstractShip testCargo = new Cargo(testCargoName, testCargoLength, testCargoWidth, testCargoDisplacement, testCargoTonnage);
        AbstractShip testTanker = new Tanker(testTankerName, testTankerLength, testTankerWidth, testTankerDisplacement, testTankerVolume);

        OdessaSeaPort odessaSeaPort = new OdessaSeaPort();



        odessaSeaPort.addShipToEndQueue(testLiner);
        odessaSeaPort.addShipToEndQueue(testCargo);
        odessaSeaPort.addShipToEndQueue(testTanker);

        int actualStatusAddShipIntoQueueFull = odessaSeaPort.addShipToEndQueue(testTanker);
        if (actualStatusAddShipIntoQueueFull != expectedStatusAddShipIntoQueueFull)
            throw new AssertionError("Queue is full expected return -1 but found " + actualStatusAddShipIntoQueueFull);

        System.out.print("OK");
    }

    @Test
    public void test2() {
        //check successful remove ship
        String testLinerName = "NameTestLiner";
        float testLinerLength = 1000;
        float testLinerWidth = 1000;
        float testLinerDisplacement = 1000;
        int testLinerPassengers = 100;
        String testCargoName = "NameTestCargo";
        float testCargoLength = 1000;
        float testCargoWidth = 1000;
        float testCargoDisplacement = 1000;
        float testCargoTonnage = 100;
        String testTankerName = "NameTestTanker";
        float testTankerLength = 1000;
        float testTankerWidth = 1000;
        float testTankerDisplacement = 1000;
        float testTankerVolume = 100;

        String expectedResultPrintShipQueueAfterRemove = "{Name=NameTestLinerLength=1000.0Width=1000.0Displacement=1000.0};{Name=NameTestTankerLength=1000.0Width=1000.0Displacement=1000.0};";

        int expectedSuccessfulStatusRemoveShipInQueue = 1;

        AbstractShip testLiner = new Liner(testLinerName, testLinerLength, testLinerWidth, testLinerDisplacement, testLinerPassengers);
        AbstractShip testCargo = new Cargo(testCargoName, testCargoLength, testCargoWidth, testCargoDisplacement, testCargoTonnage);
        AbstractShip testTanker = new Tanker(testTankerName, testTankerLength, testTankerWidth, testTankerDisplacement, testTankerVolume);

        OdessaSeaPort odessaSeaPort = new OdessaSeaPort();


        odessaSeaPort.addShipToEndQueue(testCargo);
        odessaSeaPort.addShipToEndQueue(testLiner);
        odessaSeaPort.addShipToEndQueue(testTanker);


        int actualSuccessfulStatusRemoveShipInQueue = odessaSeaPort.removeShipFromBeginQueue();
        if (actualSuccessfulStatusRemoveShipInQueue != expectedSuccessfulStatusRemoveShipInQueue)
            throw new AssertionError("Successful status remove ship in queue 1 but found " + actualSuccessfulStatusRemoveShipInQueue);

        String actualPrintShipQueueAfterRemove = odessaSeaPort.printQueueShip();
        if(!(actualPrintShipQueueAfterRemove.equals(expectedResultPrintShipQueueAfterRemove)))
            throw new AssertionError("Expected to be printed " + expectedResultPrintShipQueueAfterRemove + " but found " + actualPrintShipQueueAfterRemove);


        System.out.print("OK");
    }

}