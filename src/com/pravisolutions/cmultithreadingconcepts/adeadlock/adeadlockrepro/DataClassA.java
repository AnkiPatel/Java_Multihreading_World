package com.pravisolutions.cmultithreadingconcepts.adeadlock.adeadlockrepro;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DataClassA {
    private int onevar;
    private int twovar;
    private Lock lockOne = new ReentrantLock(true);
    private Lock lockTwo = new ReentrantLock(true);

    public void opA() {
        lockOne.lock();
        System.out.println("OpA: acquired lockOne");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onevar = 30;
        lockTwo.lock();
        twovar = 50;

        //work over
        System.out.println("OpA: releasing lockOne");
        lockOne.unlock();
        System.out.println("OpA: releasing lockTwo");
        lockTwo.unlock();

    }

    public void opB() {
        lockTwo.lock();
        System.out.println("OpB: acquired lockTwo");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        twovar = 150;
        lockOne.lock();
        System.out.println("OpB: acquired lockOne");
        onevar = 130;

        //work over
        System.out.println("OpB: releasing lockTwo");
        lockTwo.unlock();
        System.out.println("OpB: releasing lockOne");
        lockOne.unlock();

    }
}
