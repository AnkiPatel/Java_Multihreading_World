package com.vittisolutions.binterthreadcommunication.jproducerconsumerpattern.anaiveway;

class Producer implements Runnable {

    SharedBuffer sbRef;

    Producer(SharedBuffer sb){
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

    SharedBuffer sbRef;

    Consumer(SharedBuffer sb){
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


public class LearnNaiveProducerConsumer {

    public static void main(String[] args) {

        SharedBuffer bufferOfData = new SharedBuffer();
        Thread prThread = new Thread(new Producer(bufferOfData));
        prThread.setName("ProducerThread A");

        Thread csThread = new Thread(new Consumer(bufferOfData));
        csThread.setName("ConsumerThread B");

        prThread.start();
        csThread.start();

    }
}


// MIMP: Consumer and producer are running in infinite loop. you have to kill the application manually to stop it

