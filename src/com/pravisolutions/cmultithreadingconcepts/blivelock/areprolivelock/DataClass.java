package com.pravisolutions.cmultithreadingconcepts.blivelock.areprolivelock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DataClass {

    private int someData;
    private Lock lockOne = new ReentrantLock(true);
    private Lock lockTwo = new ReentrantLock(true);


    public void OpA() {
        while(true) {
            try {
                System.out.println("OpA: Acquires: lockOne");
                lockOne.tryLock(50, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(lockTwo.tryLock()) {
                System.out.println("OpA: Acquired: lockTwo");
                System.out.println("OpA doing work");
                someData += 10;
            } else {
                //Biggest mistake is "Not unlocking"
                continue;
            }
            lockOne.unlock();
            lockTwo.unlock();

        }
    }

    public void OpB() {
        while(true) {
            try {
                lockTwo.tryLock(50, TimeUnit.MILLISECONDS);
                System.out.println("OpB: Acquires: lockTwo");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(lockOne.tryLock()) {
                System.out.println("OpB: Acquired: lockOne");
                System.out.println("OpB doing work");
                someData += 10;
            } else {
                //Biggest mistake is "Not unlocking"
                continue;
            }
            lockTwo.unlock();
            lockOne.unlock();

        }
    }

}
