package com.pravisolutions.cmultithreadingconcepts.adeadlock.bsolution;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
Solution:
We should ensure that each thread acquires the locks in the same order to
avoid any cyclic dependency in lock acquisition



*/

public class DataClassB {
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
        lockOne.lock();
        System.out.println("OpB: acquired lockOne");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onevar = 130;

        lockTwo.lock();
        System.out.println("OpB: acquired lockTwo");
        twovar = 150;

        //work over
        System.out.println("OpB: releasing lockOne");
        lockOne.unlock();
        System.out.println("OpB: releasing lockTwo");
        lockTwo.unlock();


    }
}
