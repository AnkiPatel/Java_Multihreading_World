package com.vittisolutions.binterthreadcommunication.lproducerconsumerwithrlock.amyversion;

class ProducerWork implements Runnable {

    SharedBuffer sb;
    ProducerWork(SharedBuffer buffer) {
        sb = buffer;
    }

    @Override
    public void run() {
        int i = 0;
        while(true) {
            try {
                sb.produceData();
                Thread.sleep(500); // producer need time to produce the data
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class ConsumerWork implements Runnable {
    SharedBuffer sb;
    ConsumerWork(SharedBuffer buffer) {
        sb = buffer;
    }

    @Override
    public void run() {
        try {
            sb.consumeData();
            Thread.sleep(800); // Consumer needs time to consume data
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


public class LearnProducerConsumerWithRLock {

    public static void main(String[] args) {
        SharedBuffer sb = new SharedBuffer();
        Thread producer = new Thread(new ProducerWork(sb));
        Thread consumer = new Thread(new ConsumerWork(sb));

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Work done");
    }


}

/*
*  It is contionus program. you have to kill to stop it
**/