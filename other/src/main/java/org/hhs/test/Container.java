package org.hhs.test;

import java.util.ArrayList;
import java.util.List;

public class Container {
    private static volatile int index = 0;
    private List<Object> objectList = new ArrayList();
    public void add(Object object){
        objectList.add(object);
    }

    public Object getObj(){
        synchronized (objectList) {
            if (objectList.size() != 0) {
                return objectList.remove(0);
            }
            return null;
        }
    }

    public List<Object> getObjectList() {
        return objectList;
    }

    public static void main(String...args){
        final Container container = new Container();
        new Thread(new Runnable() {
            public void run() {
                Apple apple = null;
                while (true) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    apple = new Apple("apple:" + index);
                    container.getObjectList().add(apple);
                }
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Apple app = (Apple) container.getObj();
                    System.out.println(app.getName());
                }
            }
        }).start();
    }
}
