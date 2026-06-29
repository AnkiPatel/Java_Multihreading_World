package com.pravisolutions.zforInterviews.producerconsumer.cwithreentrantLockandcondition;

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
                Thread.sleep(200);
                sb.ConsumeData();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


public class DriverAppC {
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

/*
It is improved design: now consumer will never miss the signal sent by producer.
Even though it starts first.

Consumer Thread: PT2
Producer Thread: PT1
Produced data: 1
Produced data: 2
Produced data: 3
Produced data: 4
Produced data: 5
Producer Thread: PT1
Consumed data: 1
Consumed data: 2
Consumed data: 3
Consumed data: 4
Consumed data: 5
Consumer Thread: PT2
Produced data: 6
Produced data: 7
Produced data: 8
Produced data: 9
Produced data: 10
Consumed data: 6
Consumed data: 7
Consumed data: 8
Consumed data: 9
Consumed data: 10
Work done
*/
