package com.pravisolutions.dthreadwithexecutors.cscheduledexecutor;

class TaskOneForThread implements Runnable{
    public int taskid;
    public TaskOneForThread(int i) {
        taskid = i;
    }

    @Override
    public void run() {
        System.out.println("Thread " + taskid + " is downloading file for next 100 ms");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class TaskTwoForThread implements Runnable{
    public int taskid;
    public TaskTwoForThread(int i) {
        taskid = i;
    }

    @Override
    public void run() {
        System.out.println("Thread " + taskid + " is reading file next 200 ms");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
