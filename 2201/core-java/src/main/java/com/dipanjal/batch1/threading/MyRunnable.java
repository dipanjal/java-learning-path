package com.dipanjal.batch1.threading;

import java.time.LocalDateTime;

public class MyRunnable implements Runnable {
    @Override
    public void run() {
        String currentThreadName = Thread.currentThread().getName();
        for(int i=0; i<5; i++) {
            System.out.println(currentThreadName+" "+i+ " Time: "+ LocalDateTime.now());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
