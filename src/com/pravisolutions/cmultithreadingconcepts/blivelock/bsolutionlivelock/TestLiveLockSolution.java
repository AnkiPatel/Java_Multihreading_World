package com.pravisolutions.cmultithreadingconcepts.blivelock.bsolutionlivelock;



class TworkerOne implements Runnable {

    DataClass dc;
    TworkerOne(DataClass d) {
        dc = d;
    }

    @Override
    public void run() {
        dc.OpA();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class TworkerTwo implements Runnable {

    DataClass dc;
    TworkerTwo(DataClass d) {
        dc = d;
    }

    @Override
    public void run() {
        dc.OpB();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class TestLiveLockSolution {

    public static void main(String[] args) {
        DataClass dc = new DataClass();
        Thread t1 = new Thread(new TworkerOne(dc));
        Thread t2 = new Thread(new TworkerTwo(dc));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

/*
Two fixes were applied — both are important
Fix #1 — Release the lock before retrying
lockTwo.unlock(); // in OpA
lockOne.unlock(); // in OpB
This is the core fix. When a worker fails to get its second lock, it now surrenders its first lock before looping back. This breaks the standoff — the other thread now has a real chance to proceed.

Fix #2 — Random backoff (sleep)
javaThread.sleep((long) (Math.random() * 10));
Even with Fix #1, imagine both threads release and retry at the exact same millisecond, over and over. They could fall back into the same rhythm — each grabbing their first lock simultaneously, failing, releasing, and retrying in perfect sync — a livelock reborn.
The random sleep desynchronizes them, so one thread gets a head start and completes before the other retries.

Think of the hallway analogy again — Fix #1 is both people stopping. Fix #2 is one person waiting a random moment before moving, so they don't mirror each other again.
* */
