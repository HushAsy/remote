package org.hhs;

import org.hhs.remote.baseInter.Hello;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements Hello {

    public HelloImpl() throws RemoteException {
        super();
    }

    public String sayHello(String str) throws RemoteException {
        super.hashCode();
        return "hello:"+str;
    }
}
