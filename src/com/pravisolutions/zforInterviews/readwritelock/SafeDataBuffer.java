package com.pravisolutions.zforInterviews.readwritelock;

/*
Design consideration: we are making a buffer which can hold the data up to some capacity.
data is getting filled here is in order.
user can read oldest and newest data from buffer.
*/


import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SafeDataBuffer {

    private LinkedList<Integer> buffer;
    private int CAPACITY;
    private final ReadWriteLock bufferLock = new ReentrantReadWriteLock(true);
    private final Lock readLock = bufferLock.readLock();
    private final Lock writeLock = bufferLock.writeLock();
    private int counter;

    public SafeDataBuffer(int cap) {
        this.CAPACITY = cap;
        buffer = new LinkedList<>();
        counter = 0;
    }

    public void removeOldData(){
        buffer.removeFirst();
    }

    public void addInBuffer(int i) {
        writeLock.lock();
        try{
            if(buffer.size() == CAPACITY) {
                removeOldData();
            }
            counter++;
            buffer.addLast(counter+i);
            System.out.println(Thread.currentThread().getName() + " Wrote: " + (counter+i));
        } finally {
            writeLock.unlock();
        }

    }

    public int[] getBothData() {
        readLock.lock();
        try{
            int [] result = new int[2];
            if(!buffer.isEmpty()) {
                result[0] =  buffer.getFirst();
                result[1] =  buffer.getLast();
            }
            System.out.println(Thread.currentThread().getName() + " Read: " + result[0] + " " + result[1]);
            return result;
        } finally {
            readLock.unlock();
        }

    }

}
