package com.vittisolutions.athreadmanipulation.gthreadpriority;

class Task implements Runnable {

    @Override
    public void run() {
        for(int i=0; i<=5; i++) {
            System.out.println(Thread.currentThread().getName() + " count: " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class AppThreadPrioirtyLearn {

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName()); //will print main thread name
        System.out.println(Thread.currentThread().getPriority()); //will print main thread priority

        Thread lowT = new Thread(new Task(), "Low Priority Thread");
        lowT.setPriority(Thread.MIN_PRIORITY);

        Thread mediumT = new Thread(new Task(), "Medium Priority Thread");
        mediumT.setPriority(Thread.NORM_PRIORITY);

        Thread highT = new Thread(new Task(), "High Priority Thread");
        highT.setPriority(Thread.MAX_PRIORITY);

        lowT.start();
        mediumT.start();
        highT.start();

        try {
            lowT.join();
            mediumT.join();
            highT.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Often you see in result that despite setting the priority, it behaves randomly while executing thread.
        // Because it is heavily depends on underlying OS and thread scheduler.

    }
}
