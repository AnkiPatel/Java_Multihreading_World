package com.vittisolutions.binterthreadcommunication.dobjectandclasslevellocking;

class DataWithThreadWorkerClassLevel {

    //Here data and method are static, hence need to use class level locking. It is again intrinsic lock
    private static int counter;
    public static void increment() {

        synchronized (DataWithThreadWorkerClassLevel.class) {
            System.out.println(Thread.currentThread().getName() + " entered in incrementOne method");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            counter++;
            System.out.println(Thread.currentThread().getName() + " finished in incrementOne method");
        }

    }

}


class DataWithThreadWorkerObjectLevel {

    //Here data is at object level hence we use object level locking by using "this". it is intrinsic lock
    private int counter;
    public void increment() {

        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " entered in incrementOne method");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            counter++;
            System.out.println(Thread.currentThread().getName() + " finished in incrementOne method");
        }

    }
}


public class TestClassLevelLocking {

    public static void testClassLevelLock() {

        //Since method is static, it will be at class level. Both thread will try to access same
        //method execution area.

        // Below is creating Runnable object with function reference. new way in java
        Runnable r1 = DataWithThreadWorkerClassLevel::increment;

        // Below is creating Runnable object with lambda expressl
        Runnable r2 = () -> DataWithThreadWorkerClassLevel.increment();

        Thread t1 = new Thread(r1, "First Thread");
        Thread t2 = new Thread(r2, "Second Thread");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /*
    OP:
    ----
    First Thread entered in incrementOne method
    First Thread finished in incrementOne method
    Second Thread entered in incrementOne method
    Second Thread finished in incrementOne method
     */

    /*
    Explanation:
    Both thread are working on static class's memory footprint. there is object here.
    Hence, execution is sequential
    */


    public static void testObjectLevelLock() {

        //There will be two different object, hence "Synchronized(this)" is actually happening in two different object
        var obj1 = new DataWithThreadWorkerObjectLevel();
        var obj2 = new DataWithThreadWorkerObjectLevel();

        Runnable r1 = obj1::increment;
        Runnable r2 = obj2::increment;

        Thread t1 = new Thread(r1, "First Thread");
        Thread t2 = new Thread(r2, "Second Thread");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    /*
    OP:
    ----
    Second Thread entered in incrementOne method
    First Thread entered in incrementOne method
    Second Thread finished in incrementOne method
    First Thread finished in incrementOne method
     */

    /*
    Explanation:
    Since it is object level lock and both thread are running on different object (of data class), we can see
    independent execution
    */

    public static void main(String[] args) {
        //testObjectLevelLock();
        testClassLevelLock();
    }
}
