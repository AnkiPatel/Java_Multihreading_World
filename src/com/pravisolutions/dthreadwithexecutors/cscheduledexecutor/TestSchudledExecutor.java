package com.pravisolutions.dthreadwithexecutors.cscheduledexecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestSchudledExecutor {
    public static void main(String[] args) {
        int numOfThreadWeWant = 2;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(numOfThreadWeWant);

        //After first time waiting for 100 miliseconds, thread will do wake up and do TaskOne at 2000 milisecond interval
        executor.scheduleAtFixedRate(new TaskOneForThread(101), 100, 2000, TimeUnit.MILLISECONDS);

        //After first time waiting for 150 miliseconds, thread will do wake up and do TaskOne at 1000 milisecond interval
        executor.scheduleAtFixedRate(new TaskTwoForThread(505), 100, 2000, TimeUnit.MILLISECONDS);

        //IMP: above commands will run thread in given time-interval continoutsly
        //you have to make executor stop in order to stop them.

        //NOTE: Program will not end automatically as we are not shutting down the executor
    }
}
