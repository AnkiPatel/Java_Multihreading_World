package com.pravisolutions.binterthreadcommunication.kreentrantlocks.creentrantwithinnermethod;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
What we are doing:
Ans: This is the sample program. We have one queue. we add data in bulk. and then we remove older data
to maintain window size. It is to create the function calling which use the same queue.
This we do for 5 time.

Important thing to learn here is how a thread can use reentrant lock without releasing by self.

*/


class DataClass {
    private Queue<Integer> data;
    private final int WINDOWSIZE = 5;
    private Lock datalock = new ReentrantLock(true);

    DataClass() {
        data = new LinkedList<>();
    }

    public void addingVal() {
        datalock.lock();
        System.out.println("Adding val: Acquired lock");
        try {
            //Filling queue with bulk data
            for (int i = 0; i < 10; i++) {
                int ran = (int) (Math.random() * 10);
                System.out.println("Adding : " + ran);
                data.offer(ran);
            }

            trimIfOverflow();
        } finally {
            System.out.println("Adding val: releasing lock");
            datalock.unlock();
        }
    }

    public void trimIfOverflow() {
        datalock.lock();
        System.out.println("trimIfOverflow: Acquired lock");
        try {
            int overflowamount = data.size() - WINDOWSIZE;
            if (overflowamount > 0) {
                for (int i = 0; i < overflowamount; i++) {
                    System.out.println("Removing from queue: " + data.poll());
                }
            }
        } finally {
            System.out.println("trimIfOverflow: releasing lock");
            datalock.unlock();
        }
    }

    public Queue<Integer> getData() {
        return this.data;
    }

 }

class Worker implements Runnable {
    DataClass ds;
    Worker(DataClass d) {
        ds = d;
    }
    @Override
    public void run() {
        ds.addingVal();
    }
}

public class LearnReentrantlockWithInnerMethod {

    public static void main(String[] args) {
        DataClass ds = new DataClass();
        //Thread t1 = new Thread(new Worker(ds));
        Thread workThread = new Thread(()-> {
            for(int i = 0; i < 5; i++) {
                System.out.println("Calling ith time: " + i);
                ds.addingVal();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        });

        workThread.start();

        try {
            workThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(ds.getData());
    }
}
