package com.dipanjal.batch1.threading;

public class Example1 {

    public static void main(String[] args) {

        Thread.currentThread().setName("Main thread");

        Runnable printRunner = () -> {
            String currentThreadName = Thread.currentThread().getName();
            if(currentThreadName.equals("Thread 3"))
                throw new RuntimeException("Stopped Execution");
            for(int i=0; i<5; i++) {
                System.out.println(currentThreadName + " " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t1 = new Thread(printRunner);
        t1.setName("Thread 1");

        Thread t2 = new Thread(printRunner);
        t2.setName("Thread 2");

        Thread t3 = new Thread(printRunner);
        t3.setName("Thread 3");

        t1.start();
        t2.start();
        t3.start();
    }
}
