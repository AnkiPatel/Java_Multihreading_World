package com.pravisolutions.zforInterviews.readwritelock;

import java.util.ArrayList;
import java.util.Arrays;

class readWork implements Runnable {
    SafeDataBuffer buf;

    public readWork(SafeDataBuffer sbuf) {
        buf = sbuf;
    }
    @Override
    public void run() {
        //Every read thread is reading for 3 times
        try {
            for(int i=0; i<3; i++) {
                Thread.sleep(500);
                int [] result = buf.getBothData();
                System.out.println("In run: " + Arrays.toString(result));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class writeWork implements Runnable {
    SafeDataBuffer buf;

    public writeWork(SafeDataBuffer sbuf) {
        buf = sbuf;
    }
    @Override
    public void run() {
        //Every write thread is writing for 5 times
        try {
            for(int i=0; i<5; i++) {
                Thread.sleep(100);
                buf.addInBuffer(i);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


public class DriverApp {

    public static void main(String[] args) {

        SafeDataBuffer dbuf = new SafeDataBuffer(5);

        //Reader thread pool, having 7 threads
        int readThreadPoolSize = 10;
        ArrayList<Thread> myrtpool = new ArrayList<>();

        for(int i=0; i<readThreadPoolSize; i++) {
            myrtpool.add(new Thread(new readWork(dbuf)));
        }

        //Writer thread pool, having 2 threads
        int writeThreadPoolSize = 2;
        ArrayList<Thread> mywtpool = new ArrayList<>();

        for(int i=0; i<writeThreadPoolSize; i++) {
            mywtpool.add(new Thread(new writeWork(dbuf)));
        }

        //Starting threads
        for(int i=0; i<readThreadPoolSize; i++) {
            myrtpool.get(i).start();
        }

        for(int i=0; i<writeThreadPoolSize; i++) {
            mywtpool.get(i).start();
        }

        try {
            //Joining the thread
            for (int i = 0; i < readThreadPoolSize; i++) {
                myrtpool.get(i).join();
            }

            for (int i = 0; i < writeThreadPoolSize; i++) {
                mywtpool.get(i).join();
            }
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }
}
