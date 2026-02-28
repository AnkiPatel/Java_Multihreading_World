package com.vittisolutions.binterthreadcommunication.jproducerconsumerpattern.bwithfixwithwhileloop;


class Producer implements Runnable {

    ImprovedSharedBuffer sbRef;

    Producer(ImprovedSharedBuffer sb){
        sbRef = sb;
    }
    @Override
    public void run() {
        try {
            while(true) {
                sbRef.produceData();
                Thread.sleep(200); //Producer thread will sleep
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Consumer implements Runnable {

    ImprovedSharedBuffer sbRef;
    Consumer(ImprovedSharedBuffer sb){
        sbRef = sb;
    }
    @Override
    public void run() {
        try {
            while(true) {
                sbRef.consumeData();
                Thread.sleep(200); //Consumer thread will sleep
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
public class LearnFixedProducerConsumer {
    public static void main(String[] args) {

        ImprovedSharedBuffer bufferOfData = new ImprovedSharedBuffer();
        Thread prThread = new Thread(new Producer(bufferOfData));
        prThread.setName("ProducerThread A");

        Thread csThread = new Thread(new Consumer(bufferOfData));
        csThread.setName("ConsumerThread B");

        prThread.start();
        csThread.start();

    }
}
