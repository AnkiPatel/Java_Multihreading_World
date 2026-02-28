package com.vittisolutions.binterthreadcommunication.csyncronizedblock.bwithpuredataclass;



class WorkerForCounterOne implements Runnable {

    private CountersOfApp countersOfApp;

    void setDataClass(CountersOfApp obj) {
        this.countersOfApp = obj;
    }

    @Override
    public void run() {
        for(int i=0; i<200; i++) {
            countersOfApp.incrementOne();
        }
    }
}

class WorkerForCounterTwo implements Runnable {

    private CountersOfApp countersOfApp;

    void setDataClass(CountersOfApp obj) {
        this.countersOfApp = obj;
    }

    @Override
    public void run() {
        for(int i=600; i<700; i++) {
            countersOfApp.incrementTwo();
        }
    }
}

//This class have critical sections with independent object locks (not the monitor)
class CountersOfApp {

    private int counterOne;
    private int counterTwo;

    public CountersOfApp() {
        counterOne = 0;
        counterTwo = 0;
    }

    private Object lock1 = new Object();
    private Object lock2 = new Object();
    public void incrementOne() {

        // statement x;
        // above statement can be those instruction which don't need synchronization.

        synchronized (lock1) { // here lock will be at the object level and not at the class level monitor
            counterOne++;
        }

        // below statement can be those instruction which don't need synchronization.
        // statement y;
    }


    public void incrementTwo() {

        // statement x;
        // above statement can be those instruction which don't need synchronization.


        synchronized (lock2) { // here lock will be at the object level and not at the class level monitor
            counterTwo++;
        }

        // below statement can be those instruction which don't need synchronization.
        // statement y;

    }

    public void printCounterOne() {
        System.out.println(counterOne);
    }

    public void printCounterTwo() {
        System.out.println(counterTwo);
    }


}

public class LearnWithDataClass {

    public static void main(String[] args) {


        /*
        Elaborated use case,  more near to realtime application.. where critical sections are in separate data class.
        operated based on different object locks.

        Thread worker has association with the data class.
        */
        CountersOfApp sc =  new CountersOfApp();

        WorkerForCounterOne w1 = new WorkerForCounterOne();
        w1.setDataClass(sc);
        Thread t1 = new Thread(w1);

        WorkerForCounterTwo w2 = new WorkerForCounterTwo();
        w2.setDataClass(sc);
        Thread t2 = new Thread(w2);


        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        sc.printCounterOne();
        sc.printCounterTwo();
    }
}
