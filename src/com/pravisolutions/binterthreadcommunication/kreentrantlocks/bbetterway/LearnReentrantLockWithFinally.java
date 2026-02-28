package com.pravisolutions.binterthreadcommunication.kreentrantlocks.bbetterway;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
When we use synchronized block or method, we dont have liberty "when to unlock"
Here with reentrantlock you can unlock in other method also .
*/

public class LearnReentrantLockWithFinally {
    private static int counter = 0;
    private static Lock lock = new ReentrantLock(true);

    public static void increment(){
        //Critical section
        try {
            lock.lock();
            counter++;
        } finally {
            lock.unlock();
            //Instead of above statement, you can also call "unlockMylock" method. BUT not advisable
            // call unlock as soon as you done with your work.
        }
    }

    public static void unlockMylock() {
        lock.unlock();
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

        System.out.println("Running with better approach");
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
