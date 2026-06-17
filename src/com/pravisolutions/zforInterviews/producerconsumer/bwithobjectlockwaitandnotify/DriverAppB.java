package com.pravisolutions.zforInterviews.producerconsumer.bwithobjectlockwaitandnotify;
/* Design assumption: When program runs, producer produce X times data.
Means this many times producer will run the loop for pumping data.
 */

class producerWorker implements Runnable {
    int loopCount;
    SharedBuffer sb;
    producerWorker(SharedBuffer b, int count){
        sb = b;
        loopCount = count;
    }
    @Override
    public void run() {
        for(int i=0; i<loopCount; i++) {
            try {
                System.out.println("Producer Thread: " + Thread.currentThread().getName());
                Thread.sleep(200); // simulating getting the data
                sb.ProduceData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class consumerWork implements Runnable {
    SharedBuffer sb;
    int loopCount;
    consumerWork(SharedBuffer b, int count){
        sb = b;
        loopCount = count;
    }

    @Override
    public void run() {
        for(int i=0; i<loopCount; i++) {
            try {
                System.out.println("Consumer Thread: " + Thread.currentThread().getName());
                Thread.sleep(800);
                sb.ConsumeData();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


public class DriverApp {
    public static void main(String[] args) throws InterruptedException {

        SharedBuffer buffer = new SharedBuffer(5);

        Thread t1 = new Thread(new producerWorker(buffer, 2));
        Thread t2 = new Thread(new consumerWork(buffer, 2));

        t1.setName("PT1");
        t2.setName("PT2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Work done");


    }
}