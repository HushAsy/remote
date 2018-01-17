package org.hhs;

import org.hhs.remote.baseInter.Hello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        App app = new App();
        app.startSocketServer();
    }

    public void startSocketServer(){
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port);
            while (true){
                Socket socket = serverSocket.accept();
                new Thread(new ServerHanlder(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void remotePro(){
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            Hello h = new HelloImpl();
            LocateRegistry.createRegistry(8080);
            Naming.bind("rmi://127.0.0.1:8080/Hello", h);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    public class ServerHanlder implements Runnable{
        private Socket socket;
        public ServerHanlder(Socket socket){
            this.socket = socket;
        }

        public void run() {
            BufferedReader in = null;
            PrintWriter out = null;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                String currentTime = null;
                String body = null;
                while (true){
                    body = in.readLine();
                    if (body == null){
                        break;
                    }
                    System.out.println("The time server receive order: " + body);
                    currentTime = "Query time order".equalsIgnoreCase(body)?new java.util.Date(System.currentTimeMillis()).toString():"BAD ORDER";
                    out.println(currentTime);
                }
            } catch (IOException e) {
                if (in != null){
                    try {
                        try {
                            in.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }catch (Exception e1){

                    }
                }
                if (out != null){
                    out.close();
                    out = null;
                }
                if(this.socket != null) {
                    try {
                        this.socket.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    this.socket = null;
                }
            }
        }
    }
}
