package com.vittisolutions.binterthreadcommunication.kreentrantlocks.asimplestway;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
Why it is not a good approach ?
What if in increment method while doing "counter++" exception happen? lock will never get release
*/

public class LearnReentrantLockNaiveWay {
    private static int counter = 0;
    private static Lock lock = new ReentrantLock(true);

    public static void increment(){
        //Critical section
        lock.lock();
        counter++;
        lock.unlock(); // MIMP: we must explicitly has to unlock.
    }

    public static void firstThread(){
        for(int i=0;i<1000;i++){
            increment();
        }
    }

    public static void secondThread(){
        for(int i=0;i<1000;i++){
            increment();
        }
    }

    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                firstThread();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                secondThread();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(counter);

    }
}
