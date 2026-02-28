package com.pravisolutions.dthreadwithexecutors.fcallableinterfaceandfutureobject;


import java.util.concurrent.Callable;

// Task index is matched with taskData index
// Note: When here call method will give the result after thread done with it's task.
// Hence caller will get the return value in the form of future.
// Now as an implementor of the Task, we have to tell which type (Interger, double, String), caller will get.
// That type we have to provide in Callable<T> (T = type)

public class ThreadTask implements Callable<String> {

    private int id;

    public ThreadTask(int i) {
        id = i;
    }

    @Override
    public String call() throws Exception {
        //Simulating that thread needed 200 miliseconds to finish the task
        Thread.sleep(200);
        long endTime = System.currentTimeMillis();
        String retStr = "id: " + id + " fiished at " + Long.toString(endTime);
        return retStr;
    }
}
