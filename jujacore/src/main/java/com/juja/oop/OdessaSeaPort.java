package com.juja.oop;

import java.util.Arrays;

class OdessaSeaPort implements SeaPortQueue {
    private static final int NO_SHIP_IN_ARRAY = -1;
    private int indexShipInPort = NO_SHIP_IN_ARRAY;
    AbstractShip[] arrayShip = new AbstractShip[LENGTH_QUEUE_SHIP];

    @Override
    public int addShipToEndQueue(AbstractShip ship) {
        if (indexShipInPort != LENGTH_QUEUE_SHIP) {
            arrayShip[++indexShipInPort] = ship;
            return indexShipInPort;
        }
        return NO_SHIP_IN_ARRAY;
    }

    @Override
    public int removeShipFromBeginQueue() {
        if (indexShipInPort > 0 && indexShipInPort < LENGTH_QUEUE_SHIP) {
            for (int i = 1; i <indexShipInPort; i++) {
                arrayShip[i - 1] = arrayShip[i];
            }
           // System.arraycopy(arrayShip, 1, arrayShip, 1, LENGTH_QUEUE_SHIP - 1);
            arrayShip[LENGTH_QUEUE_SHIP - 1] = null;
            indexShipInPort--;
            return 1;
        }
        return NO_SHIP_IN_ARRAY;
    }

    @Override
    public String printQueueShip() {
        if (indexShipInPort != -1) {
            StringBuilder result = new StringBuilder();

            for (int i = 0; i <= indexShipInPort; i++) {
                result.append("{");
                result.append(arrayShip[indexShipInPort].toPrint());
                result.append("};");
            }

            return result.toString();
        }
        return "QueueEmpty";
    }

    public static void main(String[] args) {
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
        System.out.println(Arrays.deepToString(odessaSeaPort.arrayShip));
        odessaSeaPort.addShipToEndQueue(testLiner);
        System.out.println(Arrays.deepToString(odessaSeaPort.arrayShip));
        odessaSeaPort.addShipToEndQueue(testTanker);
        System.out.println(Arrays.deepToString(odessaSeaPort.arrayShip));


        int actualSuccessfulStatusRemoveShipInQueue = odessaSeaPort.removeShipFromBeginQueue();

        System.out.println(odessaSeaPort.printQueueShip());
        System.out.println(Arrays.deepToString(odessaSeaPort.arrayShip));
        if (actualSuccessfulStatusRemoveShipInQueue != expectedSuccessfulStatusRemoveShipInQueue)
            throw new AssertionError("Successful status remove ship in queue 1 but found " + actualSuccessfulStatusRemoveShipInQueue);

        String actualPrintShipQueueAfterRemove = odessaSeaPort.printQueueShip();

        System.out.println("ex=" + expectedResultPrintShipQueueAfterRemove);
        System.out.println("ac=" + actualPrintShipQueueAfterRemove);
        if(!(actualPrintShipQueueAfterRemove.equals(expectedResultPrintShipQueueAfterRemove)))
            throw new AssertionError("Expected to be printed " + expectedResultPrintShipQueueAfterRemove + " but found " + actualPrintShipQueueAfterRemove);


        System.out.print("OK");
    }

}