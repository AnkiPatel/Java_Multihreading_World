package com.pravisolutions.binterthreadcommunication.ereentrantlock;

class ReentrantExample {

    public synchronized void outerMethod() {
        System.out.println("Entered outerMethod");
        innerMethod(); // Calling another synchronized method
        System.out.println("Exiting outerMethod");
    }

    public synchronized void innerMethod() {
        System.out.println("Entered innerMethod");
        // Do something
        System.out.println("Exiting innerMethod");
    }
}


public class LearnReenterantBehaviourWithMonitor {

    public static void main(String[] args) {
        ReentrantExample example = new ReentrantExample();

        Runnable r1 = example::outerMethod;
        Thread t1 = new Thread(r1);
        t1.start();

        //Another way to create thread directly
        /*
        Thread thread = new Thread(example::outerMethod);
        thread.start();

         */
    }
}
