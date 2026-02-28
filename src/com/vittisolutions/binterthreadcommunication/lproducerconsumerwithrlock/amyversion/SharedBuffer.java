package com.vittisolutions.binterthreadcommunication.lproducerconsumerwithrlock.amyversion;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedBuffer {

    private int capacity;
    private List<Integer> buffer;
    private Lock bufferlock;
    private Condition productionWait;
    private Condition consumptionWait;

    SharedBuffer() {
        capacity = 5;
        buffer = new LinkedList<>();
        bufferlock = new ReentrantLock(true);
        productionWait = bufferlock.newCondition();
        consumptionWait = bufferlock.newCondition();
    }

    public void produceData() throws InterruptedException {

        bufferlock.lock();
        System.out.println("Acquired lock by producer");
        try {
            while (buffer.size() == capacity) {
                System.out.println("producer now wait");
                productionWait.await();
            }
            for (int i = 0; i < capacity; i++) {
                int val = i + 10;
                buffer.add(val);
                System.out.println("Producer: adding: " + val);
            }
            System.out.println("producer waking up consumer");
            consumptionWait.signal(); // buffer is ready to consume
        } finally {
            System.out.println("releasing lock by producer");
            bufferlock.unlock();
        }
    }

    public void consumeData() throws InterruptedException{
        bufferlock.lock();
        System.out.println("Acquired lock by consumer");
        try {
            while(buffer.isEmpty()) {
                System.out.println("consumer now wait");
                consumptionWait.await(); // consumer will wait till buffer is full
            }
            for (int i = 0; i < buffer.size(); i++) {
                System.out.println("Consumer: consuming: " + buffer.remove(0));
            }

            System.out.println("consumer waking up producer");
            productionWait.signal(); //now we have consumer, waking up producer for adding data
        } finally {
            System.out.println("releasing lock by consumer");
            bufferlock.unlock();
        }
    }
}
