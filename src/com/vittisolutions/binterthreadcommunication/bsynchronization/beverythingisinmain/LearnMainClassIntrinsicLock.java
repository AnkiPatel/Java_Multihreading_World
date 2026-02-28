package com.vittisolutions.binterthreadcommunication.bsynchronization.beverythingisinmain;

public class LearnMainClassIntrinsicLock {

    public static int counter1 = 0;
    public static int counter2 = 0;

    /*
       Learning: "LearnMainClassIntrinsicLock" object has default lock (single one).
       Here critical sections are methods, incrementCounter1 and incrementCounter2
       Problem it has: When Thread A enter the METHOD "incrementCounter1", that thread will acquire "intrinsic" lock.
       here that lock is at class level (because they are static method changing static datatype)
       Until Thread A release the lock, Thread B cannot enter even in another Critical section "incrementCounter2"
       Even though both critical sections are totally independent.
       */

    public synchronized static void incrementCounter1() {
        System.out.println("Thread which has lock: " + Thread.currentThread().getName());
        counter1++;
    }

    public synchronized static void incrementCounter2() {
        System.out.println("Thread which has lock: " + Thread.currentThread().getName());
        counter2++;
    }

    public static void main(String[] args) {

        Thread T1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i< 10; i++) {
                    incrementCounter1();
                }
            }
        });
        T1.setName("Tone");

        Thread T2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i< 10; i++) {
                    incrementCounter2();
                }
            }
        });
        T2.setName("Two");

        T1.start();
        T2.start();

        try {
            T1.join();
            T2.join();
        } catch (Exception e) {
            System.out.println("got exception: " + e.getMessage());
        }

        System.out.println(counter1);
        System.out.println(counter2);

    }
}


