package com.vittisolutions.binterthreadcommunication.bsynchronization.cdifferentclass;

public class ThreadWork{

    public int counter1 = 0;
    public int counter2 = 0;

    public synchronized void increment1() {
        System.out.println("Thread got lock is: " + Thread.currentThread().getName());
        counter1++;
    }

    public synchronized void increment2() {
        System.out.println("Thread got lock is: " + Thread.currentThread().getName());
        counter2++;
    }
}
