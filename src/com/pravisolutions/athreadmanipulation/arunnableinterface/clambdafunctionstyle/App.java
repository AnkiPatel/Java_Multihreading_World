package com.pravisolutions.athreadmanipulation.arunnableinterface.clambdafunctionstyle;

// Runnable interface is the function interface because it has only one method which need to be implemented, "run"
// Which can be encapsulated in lambda expression

//Best practice is to create the independent class implementing Runnable interface as shown in "brunnableinterface"
//Package
public class App {
    public static void main(String[] args) {

        // Lambda function style
        Runnable r1 = () -> {
            for(int i=0; i<10; i++) {
                System.out.println("Runner 1 i:= " + i);
            }
        };

        Runnable r2 = () -> {
            for(int i=130; i<140; i++) {
                System.out.println("Runner 2 i:= " + i);
            }
        };

        Thread tr1 = new Thread(r1);
        Thread tr2 = new Thread(r2);

        tr1.start();
        tr2.start();
    }
}
