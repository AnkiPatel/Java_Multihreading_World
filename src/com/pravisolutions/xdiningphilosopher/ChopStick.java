package com.pravisolutions.xdiningphilosopher;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChopStick {
    private int chopStickId;
    private Lock cslock;

    public ChopStick(int chid) {
        cslock = new ReentrantLock(true);
        chopStickId = chid;
    }

    public boolean canPickChopStick(Philosopher ps, ChopStickState state) {
        try {
            cslock.tryLock(10, TimeUnit.MILLISECONDS);
            System.out.println(ps + " is taking " + state.toString() );
            return true;

        } catch (InterruptedException e) {
            System.out.println("Caught exception: " + e.getMessage());

        }
        return false;
    }

    public void putChopStick(Philosopher ps, ChopStickState state) {
        cslock.unlock();
        System.out.println(ps + " is taking " + state.toString() );
    }

    @Override
    public String toString() {
        return "ChopStick-"+this.chopStickId;
    }
}
