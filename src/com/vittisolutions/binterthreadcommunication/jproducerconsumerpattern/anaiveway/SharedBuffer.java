package com.vittisolutions.binterthreadcommunication.jproducerconsumerpattern.anaiveway;

import java.util.LinkedList;
import java.util.List;

public class SharedBuffer {

    private int capacity;
    private List<Integer> buffer;

    SharedBuffer() {
        this.capacity = 5;
        buffer = new LinkedList<>();
        //NOTE: Using linkedlist. Consumer will consume from "head" (with remove(0) method)
        // produce will add at "tail"
    }

    public synchronized void produceData() throws InterruptedException {

        if(buffer.size() == this.capacity) {
            System.out.println("Producer going in wait");
            wait(); //wait till some consumer use the data
        }

        System.out.println("Producer adding data");
        for(int i=0; i<this.capacity; i++) {
            int val = i+10;
            System.out.println("Producing data: " + val);
            buffer.add(val);
        }
        //our work here is over so we will notify other thread to acquire lock and do their operations
        notify();
    }

    public synchronized void consumeData() throws InterruptedException {

        if(buffer.size() < this.capacity) {
            // We have to wait till buffer getting full
            wait();
        }
        System.out.println("Consumer using data");
        while(!buffer.isEmpty()) {
            System.out.println(" data: " + buffer.remove(0));
        }

        //our work here is done. now we cannot consume more so waking up other thread to produce data
        notify();

    }
}
