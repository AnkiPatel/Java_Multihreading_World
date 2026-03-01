package com.pravisolutions.xdiningphilosopher;

public class Philosopher implements Runnable{
    private int id;
    private volatile boolean isEatingFinish;
    private ChopStick leftChopstick;
    private ChopStick rightChopStick;
    private int eatingCounter;

    public Philosopher(int id, ChopStick lchs, ChopStick rchs) {
        this.id = id;
        leftChopstick = lchs;
        rightChopStick = rchs;
        isEatingFinish = false;
    }

    @Override
    public void run() {
        while(!isEatingFinish) {
            dodiscussion();
            if(this.leftChopstick.canPickChopStick(this, ChopStickState.LEFT)) {
                System.out.println(this + " acquired left choptstick");
                if(this.rightChopStick.canPickChopStick(this, ChopStickState.RIGHT)) {
                    System.out.println(this + " acquired right choptstick");
                    eatingCounter++;
                    eat();
                    this.rightChopStick.putChopStick(this, ChopStickState.RIGHT);
                }
                this.leftChopstick.putChopStick(this, ChopStickState.LEFT);
            }
        }
    }

    private void dodiscussion() {
        try {
            System.out.println( this + " is discussing ");
            int ran = (int)(Math.random() * 1000);
            Thread.sleep(ran);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void eat() {
        try {
            System.out.println( this + " is eating ");
            int ran = (int)(Math.random() * 1000);
            Thread.sleep(ran);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isEatingFinish() {
        return isEatingFinish;
    }

    public void setEatingFinish(boolean eatingFinish) {
        isEatingFinish = eatingFinish;
    }

    public int getEatingCounter() {
        return eatingCounter;
    }

    @Override
    public String toString() {
        return "Id: " + this.id;
    }
}
