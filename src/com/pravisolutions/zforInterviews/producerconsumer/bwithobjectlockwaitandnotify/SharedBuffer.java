package com.pravisolutions.zforInterviews.producerconsumer.bwithobjectlockwaitandnotify;

import java.util.LinkedList;

public class SharedBuffer {

    private LinkedList<Integer> dataBuffer;
    private int CAPACITY;
    private int SeqNumber = 0;
    private final Object olock = new Object();

    public SharedBuffer(int cap) {
        this.CAPACITY = cap;
        dataBuffer = new LinkedList<>();
    }

    public void ProduceData()  {
        try {
            synchronized (olock) {
                while (dataBuffer.size() == CAPACITY) {
                    //Need to wait till dataBuffer getting consumed fully
                    olock.wait();
                }
                for (int i = 0; i < CAPACITY; i++) {
                    SeqNumber++;
                    System.out.println("producing in buffer: " + SeqNumber);
                    dataBuffer.addFirst(SeqNumber);
                }
                olock.notify(); // currently we are thinking we have only 1 other thread. use notifyAll() if more than one thread are in waiting state and you need to wake them up
            }

        } catch (InterruptedException e) {
            System.out.println("Went wrong: production: " + e.getMessage());
            throw new RuntimeException();
        }

    }

    public void ConsumeData() {
        try {
            synchronized (olock) {
                while (dataBuffer.size() < CAPACITY) {
                    // Need to wait till buffer get full
                    olock.wait();
                }
                for (int i = 0; i < CAPACITY; i++) {
                    int data = dataBuffer.removeLast();
                    System.out.println("consumed from buffer: " + data);
                }
                olock.notify(); // currently we are thinking we have only 1 other thread. use notifyAll() if more than one thread are in waiting state and you need to wake them up
            }


        } catch (InterruptedException e){
            System.out.println("Went wrong: consumption: " + e.getMessage());
        }
    }
}
