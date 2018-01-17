package org.hhs.remote.baseInter;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
    public String sayHello(String str) throws RemoteException;
}
