package com.pravisolutions.athreadmanipulation.fdeamonthreads;

class NormalWorker implements Runnable {

    //If you notice, here infinite loop is there.
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Running from NormalWorker");
    }
}

class EverRunningNormalWorker implements Runnable {

    //If you notice, here infinite loop is there.
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Running from EverRunningNormalWorker");
        }
    }
}

class DeamonWorker implements Runnable {

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Running from DeamonWorker");
        }
    }
}


public class AppLearnDemonThread {

    public static void main(String[] args) {

        Thread everNormalWorkerThread = new Thread(new EverRunningNormalWorker());
        System.out.println(everNormalWorkerThread.isDaemon());

        // normalWorkerThread1.start();

        //Now if i dont call join() here on "normalWorkerThread", main program will exit but
        //Thread will be continued in infinite loop. because JVM by default dont kill user/worker threads.

        Thread normalWorkerThread = new Thread(new NormalWorker());
        System.out.println(normalWorkerThread.isDaemon());
        normalWorkerThread.start();

        Thread demonWorkerThread = new Thread(new DeamonWorker());
        demonWorkerThread.setDaemon(true); //Important operation
        System.out.println(demonWorkerThread.isDaemon());
        demonWorkerThread.start();

        /*
        VERY IMPORTANT TO NOTE:
        -> JVM terminates demon threads if no user/worker threads are present.
        -> Hence in our example, as soon as "normalWorkerThread"'s run method get finished, daemon thread is also
        getting terminated
        -> If we used "EverRunningNormalWorker" as user thread, main program will never able to exit and both
        user and daemon thread will continue.
        */
    }
}
