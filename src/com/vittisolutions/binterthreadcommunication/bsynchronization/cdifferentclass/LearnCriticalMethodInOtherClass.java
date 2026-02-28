package com.vittisolutions.binterthreadcommunication.bsynchronization.cdifferentclass;

public class LearnCriticalMethodInOtherClass {

    public static void main(String[] args) {

        /*
        Learning: Critical section is in "ThreadWork" class
        Problem: It is same as we have in "LearnMainClassIntrinsicLock". Only difference is this time "ThreadWork"
        object lock is being used.
        If you see output of this run, you notice that we are not at all gaining advantage of multithreading ops.
        Thread are getting executed sequentially due to Intrinsic lock
        */

        ThreadWork tw = new ThreadWork();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i<10; i++)
                    tw.increment1();
            }
        });
        t1.setName("Tone");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<10; i++)
                    tw.increment2();
            }
        });
        t2.setName("Two");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(tw.counter1);
        System.out.println(tw.counter2);

    }
}

/*
OP:
----
Thread got lock is: Tone
Thread got lock is: Tone
Thread got lock is: Tone
Thread got lock is: Tone
Thread got lock is: Tone
Thread got lock is: Tone
Thread got lock is: Tone
Thread got lock is: Tone
Thread got lock is: Tone
Thread got lock is: Tone
Thread got lock is: Two
Thread got lock is: Two
Thread got lock is: Two
Thread got lock is: Two
Thread got lock is: Two
Thread got lock is: Two
Thread got lock is: Two
Thread got lock is: Two
Thread got lock is: Two
Thread got lock is: Two
10
10
*/
