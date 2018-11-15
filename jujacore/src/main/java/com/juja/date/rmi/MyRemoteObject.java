package com.juja.date.rmi;

import java.rmi.RemoteException;

public class MyRemoteObject implements MyRemoteInterface {
    @Override
    public Integer getIncreaseNumber(Integer number) throws RemoteException {
        return ++number;
    }
}
