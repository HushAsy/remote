package org.hhs;

import org.hhs.remote.baseInter.Hello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        BlockingDeque blockingDeque = new LinkedBlockingDeque();
        int port = 8080;
        Socket socket = null;
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        try {
            socket = new Socket("127.0.0.1",port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("Query time order");
            String resp = bufferedReader.readLine();
            System.out.println("Now is:"+resp);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (printWriter != null){
                printWriter.close();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void callRemote() throws RemoteException, NotBoundException, MalformedURLException {
        Hello h = (Hello) Naming.lookup("rmi://127.0.0.1:8080/Hello");
        String str = h.sayHello("mike");
        System.out.println(str);
    }
}
