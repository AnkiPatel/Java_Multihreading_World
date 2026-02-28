package com.pravisolutions.cmultithreadingconcepts.blivelock.areprolivelock;



class TworkerOne implements Runnable {

    DataClass dc;
    TworkerOne(DataClass d) {
        dc = d;
    }

    @Override
    public void run() {
        dc.OpA();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class TworkerTwo implements Runnable {

    DataClass dc;
    TworkerTwo(DataClass d) {
        dc = d;
    }

    @Override
    public void run() {
        dc.OpB();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


public class TestLiveLockIssue {
    public static void main(String[] args) {

        DataClass dca = new DataClass();
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
