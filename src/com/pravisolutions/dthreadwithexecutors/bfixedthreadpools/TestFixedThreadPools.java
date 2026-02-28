package com.pravisolutions.dthreadwithexecutors.bfixedthreadpools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestFixedThreadPools {

    public static void main(String[] args) {
        int desiredThreads = 5;
        ExecutorService executor = Executors.newFixedThreadPool(desiredThreads);

        int numOfTasks = 25;
        for(int i=1; i<=numOfTasks; i++) {
            executor.execute(new TaskForThread(i));
        }

        //VERY IMP NOTE: Program will not end as we are not shutting down the executor
    }
}
