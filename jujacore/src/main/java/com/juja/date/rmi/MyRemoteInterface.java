package com.juja.date.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyRemoteInterface extends Remote {
    Integer getIncreaseNumber(Integer number) throws RemoteException;

}
