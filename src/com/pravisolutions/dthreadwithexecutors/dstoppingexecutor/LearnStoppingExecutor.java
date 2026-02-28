package com.pravisolutions.dthreadwithexecutors.dstoppingexecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LearnStoppingExecutor {

    public static void main(String[] args) {
        int desiredThreads = 5;
        ExecutorService executor = Executors.newFixedThreadPool(desiredThreads);

        int numOfTasks = 25;
        for(int i=1; i<=numOfTasks; i++) {
            executor.execute(new TaskForThread(i));
        }

        //Step 1: we prevent the executor to execute any new tasks
        /*
        When we call the executor dot shutdown, it is going to shut down the executor service.
        This shutdown method initiates an orderly shutdown in which previously submitted tasks are executed,
        but no new task will be accepted.
        */
        executor.shutdown();

        //Step 2: Wait till existing threads finish's their execution
        //Here we are waiting for 1000 miliseconds
        try {
            if(!executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                //again retry.
                System.out.println("Executor yet not finished all tasks.. waiting for more time");
                executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
            //Step 2A: If exception occurs we will forcefully shutdown
            executor.shutdownNow();
        }
    }
}
