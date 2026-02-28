package com.pravisolutions.binterthreadcommunication.amemorymanagment;

import static java.lang.Thread.sleep;

class Task implements Runnable {

    @Override
    public void run() {
        int num1 = 0; //This will be local to thread
        String name = "thread info";  //This will be local to thread
        for(int i=0; i<5; i++) {
            try {
                sleep(10);
                System.out.println( Thread.currentThread().getName() + " :After sleep");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(name + ": " + Thread.currentThread().getName() + ": " + num1);
            num1++;
        }
    }
}

class DataClass {
    private String info;
    private int stateNum;

    public DataClass(String ii) {
        this.info = ii;
        this.stateNum = 0;
        this.info = this.stateNum + ":" + this.info;
    }

    public void appendInfo(String info) {
        System.out.println("Prev:" + this.info);
        this.stateNum++;
        this.info = this.stateNum + ":" + info;
        System.out.println(this.info);

    }
}

class ClassWithStaticData {
    public static int counter = 0;

    public static void increment() {
        counter++;
    }
}

public class MemoryManagmentLearning {
    public static void main(String[] args) {

        DataClass sinfo = new DataClass("Init"); // Object on heap,
        // if you use same object among different thread, it will be shared
        sinfo.appendInfo("Start");

        ClassWithStaticData.increment();
        System.out.println("Counter: " + ClassWithStaticData.counter); // If we use this class's static property in the
        // thread, it will be shared. ClassWithStaticData also will be on heap memory

        Thread t1 = new Thread(new Task());
        Thread t2 = new Thread(new Task());

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        sinfo.appendInfo(("End"));
    }
}

/*
OP
----
Prev:0:Init
1:Start
Counter: 1
Thread-1 :After sleep
Thread-0 :After sleep
thread info: Thread-0: 0
thread info: Thread-1: 0
Thread-0 :After sleep
thread info: Thread-0: 1
Thread-0 :After sleep
Thread-1 :After sleep
thread info: Thread-0: 2
thread info: Thread-1: 1
Thread-0 :After sleep
thread info: Thread-0: 3
Thread-1 :After sleep
thread info: Thread-1: 2
Thread-0 :After sleep
Thread-1 :After sleep
thread info: Thread-0: 4
thread info: Thread-1: 3
Thread-1 :After sleep
thread info: Thread-1: 4
Prev:1:Start
2:End
*/