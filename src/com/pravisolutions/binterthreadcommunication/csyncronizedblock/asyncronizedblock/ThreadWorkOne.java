package com.pravisolutions.binterthreadcommunication.csyncronizedblock.asyncronizedblock;


class ThreadWorkOne {

    private int counterOne;
    private int counterTwo;

    public ThreadWorkOne() {
        counterOne = 0;
        counterTwo = 0;
    }


    public void incrementOne() {

        int a = 10+29;
        // above statement can be those instruction which don't need synchronization.

        synchronized (this) { // here we are still using intrinsic lock of object "ThreadWorkOne"
            counterOne++;
        }

        // below statement can be those instruction which don't need synchronization.
       a = 89;
    }


    public void incrementTwo() {

        int b = 92 + 33;
        // above statement can be those instruction which don't need synchronization.

        synchronized (this) { // here we are still using intrinsic lock of object "ThreadWorkOne"
            counterTwo++;
        }
        // below statement can be those instruction which don't need synchronization.
        b = 934-8;
    }



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

