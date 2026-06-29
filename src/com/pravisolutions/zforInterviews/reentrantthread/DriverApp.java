package com.pravisolutions.zforInterviews.reentrantthread;

import java.util.ArrayList;
import java.util.Arrays;

class TaskRunner implements Runnable {

    DataForOps dataRef;

    public TaskRunner(DataForOps ref) {
        this.dataRef = ref;
    }

    @Override
    public void run() {
        int startIndex = dataRef.getDataSize()-1;
        int result = dataRef.getSumOfElement(0, startIndex);
        System.out.println("Result: " + result);
    }
}


public class DriverApp {

    public static void main(String[] args) {

        ArrayList<Integer> dlist = new ArrayList<>(Arrays.asList(1,2,3,4,5));
        DataForOps data = new DataForOps(dlist);

        Runnable r1 = new TaskRunner(data);
        Thread t1 = new Thread(r1);

        t1.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
