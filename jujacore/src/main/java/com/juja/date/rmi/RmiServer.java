package com.juja.date.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer {
    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        MyRemoteInterface myRemoteObject = new MyRemoteObject();
        try {
            MyRemoteInterface stub = (MyRemoteInterface) UnicastRemoteObject.exportObject(myRemoteObject, 0);
            Registry registry = LocateRegistry.createRegistry(1099);

            registry.bind("MyRemoteObject", stub);
            System.out.println("bound 'MyRemoteObject'");
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}


