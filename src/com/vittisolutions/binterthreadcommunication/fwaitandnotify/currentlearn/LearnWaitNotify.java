package com.vittisolutions.binterthreadcommunication.fwaitandnotify.currentlearn;


class TaskProcessor {

    public void produceData() throws InterruptedException {
        System.out.println("In produceData");
        //IMP: It is using object intrinsic lock
        //as mentioned in "LearnMainClassIntrinsicLock", Once thread acquire monitor or intrinsic lock, it will release
        //it only after finishing it's work in the given critical section. We can repair this issue by wait(), notify()
        //notifyAll().
        synchronized (this) {
            System.out.println("producer: Acquired lock by producer..");
            System.out.println("Producing data : By producer");
            System.out.println("producer: Triggering wait()");
            wait();
            // wait() means: "I did had the lock but already done with my work. so releasing it.
            // and in the future, I will be needing the lock for further work.
            System.out.println("producer: Wait is over.. After wait print");

        }
    }

    public void consumeData() throws InterruptedException {
        System.out.println("In consumeData");
        //IMP: It is using object intrinsic lock

        //With sleep, we are giving oppertunity to other thread to acquire the locak
        Thread.sleep(500); //sleep for 500 miliseconds

        synchronized (this) {
            System.out.println("consumer: Acquired lock by consumer");
            System.out.println("Consuming data : By consumer");
            System.out.println("consumer: Before notify()");
            notify();
            //IMP: Lock will not release immediately by JAVA and further operation of this function will be continued.

            System.out.println("consumer: After notify()");
        }
    }
}

/* IMP: Is syncronized block deterministic?
Ans: No. That's why we should not use it when multiple thread are in picture.
Explaination: As per notify() document: "Wakes up a single thread that is waiting on this object's monitor.
If any threads are waiting on this object, one of them is chosen to be awakened.
The choice is arbitrary and occurs at the discretion of the implementation."
which means, if 5 other threads are in wait() state, randomly one out of it will be awakened on notify. Programmer dont
have control over it and behaviour become un-deterministic.
            */

public class LearnWaitNotify {

    public static void main(String[] args) {

        // Using Lambda . just for leaning

        var processExecutor = new TaskProcessor();

        //Instead of creating runnable object explicitly,
        // creating anonymous object of "Runnable" through lambda
        Thread t1 = new Thread(() -> {
            try {
                processExecutor.produceData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // Creating object of runnable through lambda, and then creating thread
        Runnable r2 = () -> {
            try {
                processExecutor.consumeData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };;
        Thread t2 = new Thread(r2);

        //Older style
        /*
        Runnable r3 = new Runnable() {
            @Override
            public void run() {
                try {
                    processExecutor.consumeData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Thread t3 = new Thread(r3);
        */

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("In: " + Thread.currentThread().getName());

    }
}


/*
OP:
----
In produceData
producer: Acquired lock by producer..
Producing data : By producer
In consumeData
producer: Triggering wait()
consumer: Acquired lock by consumer
Consuming data : By consumer
consumer: Before notify()
consumer: After notify()
producer: Wait is over.. After wait print
In: main
 */
