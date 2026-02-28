package com.pravisolutions.dthreadwithexecutors.asinglethreadedexecutor;

public class TaskForThread implements Runnable {
    public int taskid;
    public TaskForThread(int i) {
        taskid = i;
    }

    @Override
    public void run() {
        System.out.println("Thread " + taskid + " doing work for next 200 ms");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
