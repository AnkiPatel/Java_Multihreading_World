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

/*
OP:
---
Thread 5th acquired the semaphore
Thread 2th acquired the semaphore
Thread 1th acquired the semaphore
Thread 3th acquired the semaphore
Thread 4th acquired the semaphore
Table used by: 3th
Table used by: 2th
Table used by: 5th
Table used by: 1th
Table used by: 4th
Thread 4th done with table, releasing
Thread 3th done with table, releasing
Thread 5th done with table, releasing
Thread 9th acquired the semaphore
Thread 2th done with table, releasing
Thread 7th acquired the semaphore
Table used by: 7th
Thread 1th done with table, releasing
Thread 8th acquired the semaphore
Table used by: 8th
Thread 6th acquired the semaphore
Thread 10th acquired the semaphore
Table used by: 9th
Table used by: 10th
Table used by: 6th
Thread 7th done with table, releasing
Thread 9th done with table, releasing
Thread 6th done with table, releasing
Thread 8th done with table, releasing
Thread 10th done with table, releasing
*/
