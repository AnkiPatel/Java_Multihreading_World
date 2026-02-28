package com.pravisolutions.athreadmanipulation.croleofsleep;

class Runner1 implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for(int i=0; i<10; i++) {
            System.out.println("Runner 1 i:= " + i);
        }
    }
}

class Runner2 implements Runnable{
    @Override
    public void run() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for(int i=130; i<140; i++) {
            System.out.println("Runner 2 i:= " + i);
        }
    }
}

public class App {

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runner1());
        Thread t2 = new Thread(new Runner2());

        t1.start();
        t2.start();
    }
}
