package com.dipanjal.batch1.threading;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class InterruptedThreadExample {
    private static final Logger logger = Logger
            .getLogger(InterruptedThreadExample.class.getName());
    public static void main(String[] args) {
        Runnable r = () -> {
            Thread currentThread = Thread.currentThread();
            for(int i=1; i<=10; i++) {
                System.out.println(currentThread.getName()+" "+i+ " Time: "+ LocalDateTime.now());
                try {
                    Thread.sleep(1000);
                    if(i % 3 == 0) {
                        currentThread.interrupt();
                    }
                } catch (InterruptedException e) {
                    logger.info(currentThread.getName()+" "+i+" has interrupted");
                }
            }


        };
        new Thread(r, "Thread 1").start();
    }

}
