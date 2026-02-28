package com.pravisolutions.cmultithreadingconcepts.adeadlock.bsolution;

class TworkerOne implements Runnable {

    DataClassB dc;
    TworkerOne(DataClassB d) {
        dc = d;
    }

    @Override
    public void run() {
        dc.opA();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class TworkerTwo implements Runnable {

    DataClassB dc;
    TworkerTwo(DataClassB d) {
        dc = d;
    }

    @Override
    public void run() {
        dc.opB();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class LearnDeadlockSolution {

    public static void main(String[] args) {
        DataClassB dcb = new DataClassB();
        Thread t1 = new Thread(new TworkerOne(dcb));
        Thread t2 = new Thread(new TworkerTwo(dcb));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
