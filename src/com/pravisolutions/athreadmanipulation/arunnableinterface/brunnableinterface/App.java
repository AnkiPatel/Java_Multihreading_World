package com.pravisolutions.athreadmanipulation.arunnableinterface.brunnableinterface;

class Runner1 implements Runnable{

    @Override
    public void run() {
        for(int i=0; i<10; i++) {
            System.out.println("Runner 1 i:= " + i);
        }
    }
}

class Runner2 implements Runnable{
    @Override
    public void run() {
        for(int i=130; i<140; i++) {
            System.out.println("Runner 2 i:= " + i);
        }
    }
}

public class App {

    public static void main(String[] args) {
        Thread runner1 = new Thread(new Runner1());
        Thread runner2 = new Thread(new Runner2());

        runner1.start();
        runner2.start();

    }
}

/**
 OP:
 --
 Runner 1 i:= 0
 Runner 1 i:= 1
 Runner 1 i:= 2
 Runner 1 i:= 3
 Runner 2 i:= 130
 Runner 2 i:= 131
 Runner 2 i:= 132
 Runner 2 i:= 133
 Runner 2 i:= 134
 Runner 2 i:= 135
 Runner 2 i:= 136
 Runner 2 i:= 137
 Runner 1 i:= 4
 Runner 1 i:= 5
 Runner 1 i:= 6
 Runner 1 i:= 7
 Runner 1 i:= 8
 Runner 2 i:= 138
 Runner 2 i:= 139
 Runner 1 i:= 9
 */
