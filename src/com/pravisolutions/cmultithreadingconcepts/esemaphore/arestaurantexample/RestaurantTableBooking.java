package com.pravisolutions.cmultithreadingconcepts.esemaphore.arestaurantexample;

import java.util.concurrent.Semaphore;

public class RestaurantTableBooking {
    //We have 5 tables in the restaurant
    private Semaphore sm = new Semaphore(5, true);

    public void allocateTable() {
        String tname = Thread.currentThread().getName();
        try {
            sm.acquire();
            System.out.println("Thread " + tname + " acquired the semaphore");
            assignTheTable(tname);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            //In any case we have to release the semaphore
            System.out.println("Thread " + tname + " done with table, releasing");
            sm.release();
        }
    }

    private void assignTheTable(String threadName) {
        //Just imagine, assigning table + using the table takes 1000 miliseconds
        try {
            System.out.println("Table used by: " +  threadName);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
