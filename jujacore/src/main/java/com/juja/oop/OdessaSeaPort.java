package com.juja.oop;

import java.util.Arrays;
import java.util.Comparator;

public class OdessaSeaPort implements SeaPortQueue {
    private static final int NO_SHIP_IN_ARRAY = -1;
    private int indexShipInPort = NO_SHIP_IN_ARRAY;
    private AbstractShip[] arrayShip = new AbstractShip[LENGTH_QUEUE_SHIP];

    @Override
    public int addShipToEndQueue(AbstractShip ship) {
        if (indexShipInPort >= NO_SHIP_IN_ARRAY && indexShipInPort != LENGTH_QUEUE_SHIP - 1) {
            arrayShip[++indexShipInPort] = ship;
            System.out.println("add i " +indexShipInPort);
            return indexShipInPort;
        }
        return NO_SHIP_IN_ARRAY;
    }

    @Override
    public int removeShipFromBeginQueue() {
        if (indexShipInPort >= 0 && indexShipInPort < LENGTH_QUEUE_SHIP) {
            System.arraycopy(arrayShip, 1, arrayShip, 0, indexShipInPort);
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
                result.append(arrayShip[i].toPrint());
                result.append("};");
            }
            return result.toString();
        }
        return "QueueEmpty";
    }

    public static String sortSumPaymentAsc(AbstractShip[] arrayShips) {
        if (arrayShips == null) {
            return "";
        }
        Arrays.sort(arrayShips, new Comparator<AbstractShip>() {
            @Override
            public int compare(AbstractShip o1, AbstractShip o2) {
                return (int) ((int) o1.calculatePayment() - o2.calculatePayment());
            }
        });
        StringBuilder result = new StringBuilder();
        for (AbstractShip ship : arrayShips) {
            result.append(ship.getName()).append("=").append(ship.calculatePayment());
        }
        return result.toString();
    }
}