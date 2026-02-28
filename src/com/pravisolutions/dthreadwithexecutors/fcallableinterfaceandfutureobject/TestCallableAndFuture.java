package com.pravisolutions.dthreadwithexecutors.fcallableinterfaceandfutureobject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TestCallableAndFuture {
    public static void main(String[] args) {
        List<Future<String>> taskDataResult;

        int threadpoolsize = 5;
        taskDataResult = new ArrayList<>(5);
        ExecutorService exec = Executors.newFixedThreadPool(threadpoolsize);
        for(int i=1; i<=threadpoolsize; i++) {
            Future<String> taskResult =  exec.submit(new ThreadTask(i));
            taskDataResult.add(taskResult);
        }

        for(int i=0; i<taskDataResult.size(); i++) {
            Future<String> f = taskDataResult.get(i);
            try {
                System.out.println(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }


        //----------- Stopping executor -----------------
        exec.shutdown();

        //Step 2: Wait till existing threads finish's their execution
        //Here we are waiting for 1000 miliseconds
        try {
            if(!exec.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                //again retry.
                System.out.println("Executor yet not finished all tasks.. waiting for more time");
                exec.awaitTermination(1000, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
            //Step 2A: If exception occurs we will forcefully shutdown
            exec.shutdownNow();
        }

    }
}
