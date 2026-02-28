package com.pravisolutions.dthreadwithexecutors.asinglethreadedexecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestSingleThreadExecutor {
    public static void main(String[] args) {
        ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
        // one thread will be there.. we can assign multiple tasks to SingleThreadExecutor
        // All assigned tasks will be handled one by one with that single thread

        for(int i=0; i<4; i++) {
            threadExecutor.execute(new TaskForThread(i));
        }

        //VERY IMP NOTE: Program will not end as we are not shutting down the executor
    }
}
