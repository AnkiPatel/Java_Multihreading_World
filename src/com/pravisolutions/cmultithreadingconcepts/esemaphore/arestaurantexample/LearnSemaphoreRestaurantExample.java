package com.pravisolutions.cmultithreadingconcepts.esemaphore.arestaurantexample;

import java.util.LinkedList;
import java.util.List;

public class LearnSemaphoreRestaurantExample {
    public static void main(String[] args) {

        RestaurantTableBooking booking = new RestaurantTableBooking();
        //We are creating 10 threads, behaving as 10 different customers waiting to get table
        List<Thread> mythreadpool = new LinkedList<>();

        for(int i=1; i<=10; i++) {
            Thread t = new Thread(() -> {
                booking.allocateTable();
            });
            t.setName(i+"th");
            mythreadpool.add(t);
        }

        for(Thread t : mythreadpool) {
            t.start();
        }

        try {
            for (Thread t : mythreadpool) {
                t.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
