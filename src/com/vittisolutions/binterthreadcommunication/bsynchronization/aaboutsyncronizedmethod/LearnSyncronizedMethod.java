package com.vittisolutions.binterthreadcommunication.bsynchronization.aaboutsyncronizedmethod;

class TaskWithSimpleCounter implements Runnable {

    @Override
    public void run() {
        for(int i = 0; i< 1000; i++) {
            SimpleCounter.increment();
        }
    }
}

class TaskwithSharedCounter implements Runnable {

    @Override
    public void run() {
        for(int i = 0; i< 1000; i++) {
            SharedCounter.increment();
        }
    }
}


class SimpleCounter {

    public static int counter = 0;
    public static void increment() {
        counter++;
    }
}

class SharedCounter {

    public static int counter = 0;
    //NOTE: Here underneath "synchronized" keyword is telling JVM to use intrinsic a.k.a monitor
    // lock of Class SharedCounter (because static method is at class level)
    public synchronized static void increment() {
        counter++;
    }
}


public class LearnSyncronizedMethod {

    public static void main(String[] args) {

        //scenario : two thread ard accessing same value
        Thread t1 = new Thread(new TaskWithSimpleCounter());
        t1.setName("Taskone");
        Thread t2 = new Thread(new TaskWithSimpleCounter());
        t2.setName("Tasktwo");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("SimpleCounter: " + SimpleCounter.counter);
        /**
         * Learning: If you see the output closely, Coutner value is not reaching to 2000
         * Even if you re-run the program, value is indeterministic. For counter value perspective, it will be data incosistency
         */

        //================== now with synchronized method ===================

        Thread t3 = new Thread(new TaskwithSharedCounter());
        t3.setName("Taskthree");
        Thread t4 = new Thread(new TaskwithSharedCounter());
        t4.setName("Taskfour");

        t3.start();
        t4.start();

        try {
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("SharedCounter: " + SharedCounter.counter);
        /**
         * Learning: "synchronized static void increment()" in SharedCounter class made sure that at a time only
         * one thread can do the operation with help of intrinsic lock
         * NOTE: what is intrinsic lock:  This is a type of intrinsic lock,
         * meaning that every object in Java has an implicit lock. When a thread acquires this lock,
         * it prevents other threads from entering synchronized sections of code for that
         * object until the lock is released.
         *
         * in our case intrinsic lock of "SharedCounter" object has been used.
        */
    }
}

/*
OP:
SimpleCounter: 1665
SharedCounter: 2000
*/