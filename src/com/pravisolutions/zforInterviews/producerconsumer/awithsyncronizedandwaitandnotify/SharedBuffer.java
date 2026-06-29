package com.pravisolutions.zforInterviews.producerconsumer.awithsyncronizedandwaitandnotify;

import java.util.LinkedList;

public class SharedBuffer {

    private LinkedList<Integer> dataBuffer;
    private int CAPACITY;
    private int SeqNumber = 0;

    public SharedBuffer(int cap) {
        this.CAPACITY = cap;
        dataBuffer = new LinkedList<>();
    }

    public synchronized void ProduceData()  {
        try {
            while(dataBuffer.size() == CAPACITY) {
                //Need to wait till dataBuffer getting consumed fully
                wait();
            }
            for(int i=0; i<CAPACITY; i++) {
                SeqNumber++;
                System.out.println("producing in buffer: " + SeqNumber);
                dataBuffer.addFirst(SeqNumber);
            }
            notify(); // currently we are thinking we have only 1 other thread. use notifyAll() if more than one thread are in waiting state and you need to wake them up
        } catch (InterruptedException e) {
            System.out.println("Went wrong: production: " + e.getMessage());
            throw new RuntimeException();
        }

    }

    public synchronized void ConsumeData() {
        try {
            while(dataBuffer.size() < CAPACITY) {
                // Need to wait till buffer get full
                wait();
            }
            for(int i=0; i<CAPACITY; i++) {
                int data = dataBuffer.removeLast();
                System.out.println("consumed from buffer: " + data);
            }
            notify(); // currently we are thinking we have only 1 other thread. use notifyAll() if more than one thread are in waiting state and you need to wake them up

        } catch (InterruptedException e){
            System.out.println("Went wrong: consumption: " + e.getMessage());
        }
    }
}
