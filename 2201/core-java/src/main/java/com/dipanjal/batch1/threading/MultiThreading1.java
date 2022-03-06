package com.dipanjal.batch1.threading;

public class MultiThreading1 {

    public void printer() {
        System.out.println("My printer");
        for(int i=0; i<5; i++){
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" executing Runnable 2");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable rLambda = () -> {
            System.out.println(Thread.currentThread().getName()+" executing Runnable Lambda");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread t3 = new Thread(rLambda,"Thread 3");
        t3.start();

        Thread t1 = new Thread(myRunnable);
        t1.setName("Thread 1");
        Thread t2 = new Thread(myRunnable);
        t2.setName("Thread 2");
        t1.start();
        t2.start();

//        t3.start();

//        Thread t1 = new Thread() {
//            final String threadName = "Thread 1";
//            @Override
//            public void run() {
//                for(int i=0; i<5; i++) {
//                    System.out.println(threadName+" "+i+ " Time: "+ LocalDateTime.now());
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//
//        t1.start();
//
//        Thread t2 = new Thread() {
//            final String threadName = "Thread 2";
//            @Override
//            public void run() {
//                for(int i=0; i<5; i++) {
//                    System.out.println(threadName+" "+i+ " Time: "+ LocalDateTime.now());
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//
//        t2.start();
    }
}
