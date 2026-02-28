package com.pravisolutions.cmultithreadingconcepts.cvolatile;

class SomeData {
    private volatile int anInt = 10;

    public synchronized void incrementData() {
        anInt += 25;
    }

    public int getData() {
        return anInt;
    }

}

class Worker implements Runnable {
    private boolean terminated = false;
    SomeData sd;
    Worker(SomeData psd) {
        sd = psd;
    }

    @Override
    public void run() {
        while(!terminated) {
            sd.incrementData();
            System.out.println("data after run: " + sd.getData());
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setTerminated() {
        this.terminated = true;
    }
}

public class LearnVolatile {

    public static void main(String[] args) {
        SomeData sd = new SomeData();
        Worker w = new Worker(sd);
        Thread t1 = new Thread(w);

        t1.start();

        //Making sleep main thread for 500 milliseconds
        try {
            Thread.sleep(500);
            w.setTerminated();
            t1.join();
            System.out.println("Final value for data: " + sd.getData());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
