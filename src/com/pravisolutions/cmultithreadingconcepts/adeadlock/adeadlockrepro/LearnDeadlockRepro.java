package com.pravisolutions.cmultithreadingconcepts.adeadlock.adeadlockrepro;

class TworkerOne implements Runnable {

    DataClassA dc;
    TworkerOne(DataClassA d) {
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

    DataClassA dc;
    TworkerTwo(DataClassA d) {
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

public class LearnDeadlockRepro {

    public static void main(String[] args) {
        DataClassA dca = new DataClassA();
        Thread t1 = new Thread(new TworkerOne(dca));
        Thread t2 = new Thread(new TworkerTwo(dca));

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
