package com.vittisolutions.athreadmanipulation.arunnableinterface.dannonymousthreadinnerclass;

//Crating Thread with anonymous runnable interface implementor class
public class App {
    public static void main(String[] args) {
        Thread tr1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<10; i++) {
                    System.out.println("Runner 1 i:= " + i);
                }
            }
        });

        Thread tr2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=130; i<140; i++) {
                    System.out.println("Runner 2 i:= " + i);
                }
            }
        });

        tr1.start();
        tr2.start();
    }
}
