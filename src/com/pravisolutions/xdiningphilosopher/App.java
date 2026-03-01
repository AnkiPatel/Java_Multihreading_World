package com.pravisolutions.xdiningphilosopher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = null;
        Philosopher[] philosophers = null;

        try{

            philosophers = new Philosopher[Constants.NO_OF_TABLESPACE];
            ChopStick[] chopSticks = new ChopStick[Constants.NO_OF_TABLESPACE];

            for(int i=0;i<Constants.NO_OF_TABLESPACE;i++){
                chopSticks[i] = new ChopStick(i);
            }

            executorService = Executors.newFixedThreadPool(Constants.NO_OF_TABLESPACE);

            for(int i=0;i<Constants.NO_OF_TABLESPACE;i++){
                philosophers[i] = new Philosopher(i, chopSticks[i], chopSticks[(i+1) % Constants.NO_OF_TABLESPACE]);
                executorService.execute(philosophers[i]);
            }

            //Main thread sleeping, simulating meeting
            Thread.sleep(Constants.MEETING_SIMULATION_RUNNING_TIME);

            for(Philosopher philosopher : philosophers){
                philosopher.setEatingFinish(true);
            }
        }finally{

            executorService.shutdown();

            while(!executorService.isTerminated()){
                Thread.sleep(1000);
            }

            for(Philosopher philosopher : philosophers ){
                System.out.println(philosopher+" eat #"+philosopher.getEatingCounter());
            }

        }

    }
}
