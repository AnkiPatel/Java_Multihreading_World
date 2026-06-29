package com.pravisolutions.zforInterviews.producerconsumer.awithsyncronizedandwaitandnotify;
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


public class DriverAppA {
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
* Not and efficient way: If consumer thread miss the "notify" triggered by producer, it will be keep waiting
* That's why we have added sleep of 800 millisecond in consumer worker to give "time" to producer to produce data.
*/

/*
OP:
Producer Thread: PT1
Consumer Thread: PT2
producing in buffer: 1
producing in buffer: 2
producing in buffer: 3
producing in buffer: 4
producing in buffer: 5
Producer Thread: PT1
consumed from buffer: 1
consumed from buffer: 2
consumed from buffer: 3
consumed from buffer: 4
consumed from buffer: 5
Consumer Thread: PT2
producing in buffer: 6
producing in buffer: 7
producing in buffer: 8
producing in buffer: 9
producing in buffer: 10
consumed from buffer: 6
consumed from buffer: 7
consumed from buffer: 8
consumed from buffer: 9
consumed from buffer: 10
Work done

*/
