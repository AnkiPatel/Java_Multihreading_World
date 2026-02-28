package com.vittisolutions.binterthreadcommunication.csyncronizedblock.asyncronizedblock;


class ThreadWorkTwo {

    private int counterOne;
    private int counterTwo;

    public ThreadWorkTwo() {
        counterOne = 0;
        counterTwo = 0;
    }

    private Object lock1 = new Object();
    private Object lock2 = new Object();
    public void incrementOne() {

        int a = 10+29;
        // above statement can be those instruction which don't need synchronization.

        synchronized (lock1) { // here we are using seperate object "lock1" for locking the block.
            // Nothing to do with intrinsic lock of "ThreadWorkTwo"
            counterOne++;
        }

        // below statement can be those instruction which don't need synchronization.
       a = 89;
    }


    public void incrementTwo() {

        int b = 92 + 33;
        // above statement can be those instruction which don't need synchronization.


        synchronized (lock2) { // here we are using seperate object "lock1" for locking the block.
            // Nothing to do with intrinsic lock of "ThreadWorkTwo"
            counterTwo++;
        }

        // below statement can be those instruction which don't need synchronization.
        b = 934-8;
    }

    /**So here if thread-a is holding lock for "counterOne", thread-b can still use "counterTwo" by acquiring
     independent lock and proceed. If you put "Synchronized" keyword on method, you cannot achieve because it will
     be intrinsic lock
     */

    public void execute() {
        Thread t1 = new Thread( ()->{
            for(int i=0; i<200; i++)
                incrementOne();
        });

        Thread t2 = new Thread( () -> {
            for(int i=0; i<200; i++)
                incrementTwo();
        });

        t1.start();
        t2.start();

        //Because we are NOT using intrinsic lock, both threads are working
        // in parallel accessing different critical sections/

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(this.counterOne);
        System.out.println(this.counterTwo);
    }

}

