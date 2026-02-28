package com.pravisolutions.athreadmanipulation.bthreadclass.awiththreadclass;

class ThreadRunner1 extends Thread{

    @Override
    public void run() {
        for(int i=0; i<10; i++) {
            System.out.println("Runner 1 i:= " + i);
        }
    }
}

class ThreadRunner2 extends Thread{
    @Override
    public void run() {
        for(int i=130; i<140; i++) {
            System.out.println("Runner 2 i:= " + i);
        }
    }
}

public class App {

    public static void main(String[] args) {
        Thread t1 = new ThreadRunner1();
        Thread t2 = new ThreadRunner2();

        t1.start();
        t2.start();

    }
}
