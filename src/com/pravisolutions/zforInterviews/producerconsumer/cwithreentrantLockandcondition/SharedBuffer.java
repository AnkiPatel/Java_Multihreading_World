package com.pravisolutions.zforInterviews.producerconsumer.cwithreentrantLockandcondition;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedBuffer {

    private LinkedList<Integer> dataBuffer;
    private int CAPACITY;
    private int SeqNumber = 0;

    //For synchronization
    private Lock dataLock ;
    private Condition producerCondition ;
    private Condition consumerCondition;

    public SharedBuffer(int cap) {
        this.CAPACITY = cap;
        dataBuffer = new LinkedList<>();
        dataLock = new ReentrantLock(true);
        producerCondition = dataLock.newCondition();
        consumerCondition = dataLock.newCondition();

    }

    public void ProduceData() throws InterruptedException {
        dataLock.lock();
        try {
            while(dataBuffer.size() == CAPACITY) {
                producerCondition.await();
            }
            //At this lock thread has lock
            for(int i=0; i<CAPACITY; i++) {
                SeqNumber++;
                System.out.println("Produced data: " + SeqNumber);
                this.dataBuffer.addFirst(SeqNumber);
            }
            consumerCondition.signal(); //notifying consumer

        } catch (InterruptedException e) {
            System.out.println("Caught exception: " + e.getMessage());
        } finally {
            dataLock.unlock();
        }


    }

    public void ConsumeData() {
        dataLock.lock();
        try {
            while(dataBuffer.size() < CAPACITY) {
                consumerCondition.await();
            }
            for(int i=0; i<CAPACITY; i++) {
                int data = dataBuffer.removeLast();
                System.out.println("Consumed data: " + data);
            }
            producerCondition.signal();
        } catch (InterruptedException e) {
            System.out.println("Caught exception: " + e.getMessage());
        } finally {
            dataLock.unlock();
        }
    }
}
