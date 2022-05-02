package com.dipanjal.batch1.references;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class JavaReferences {

    public static class User {
        public int id ;
        public String name;

        public User(int id, String name) {
            this.name = name;
            this.id = id;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static class UserPhantom extends PhantomReference<User> {

        public UserPhantom(User referent, ReferenceQueue<? super User> q) {
            super(referent, q);
        }

        public void finalizeResources() {
            // free resources
            System.out.println("clearing other dependent objects ...");
        }
    }

    private static void strongReference() {
        User user = new User(1, "Erik"); //creating strong reference
        System.out.println(user);
        user = null; //eligible for gc
        System.gc();
        System.out.println(user);
    }

    private static void weakReference() {
        User user = new User(1, "Erik"); //creating strong reference
        WeakReference<User> userWeakReference = new WeakReference<>(user);
        System.out.println(userWeakReference.get());
        user = null; //eligible for gc
        System.out.println(userWeakReference.get());
        System.gc(); //cleaning the heap memory
        /**
         * usecase: database connection object
         * reason: instant clean up
         */
        System.out.println(userWeakReference.get());
    }

    private static void softReference() {
        User user = new User(1, "Erik"); //creating strong reference
        SoftReference<User> userSoftReference = new SoftReference<>(user);
        System.out.println(userSoftReference.get());
        user = null; //eligible for gc
        System.out.println(userSoftReference.get());
        System.gc(); //cleaning the heap memory
        System.out.println(userSoftReference.get());
    }

    private static void phantomReference() {
//        User user = new User(1, "Erik"); //creating strong reference
//        ReferenceQueue<User> referenceQueue = new ReferenceQueue<>();
//        PhantomReference<User> userPhantomReference = new PhantomReference<>(user, referenceQueue);
        User writer = new User(1, "Erick"); //long running object
        ReferenceQueue<User> queue = new ReferenceQueue<>();
        PhantomReference<User> userPhantomReference = new PhantomReference<>(writer, queue);
//        UserPhantom userPhantom = new UserPhantom(
//                user,
//                queue
//        );
        writer = null;
        System.gc();

//        Reference<?> reference = queue.poll();
        if(userPhantomReference.isEnqueued()) {
            cleanUpResources();
        }
//        System.out.println(reference.isEnqueued());
//        if(reference.isEnqueued()){
//            userPhantom.finalizeResources();
//        }
//        System.out.println(userPhantomReference.get());

        System.out.println("-------");
    }

    private static void cleanUpResources() {
        System.out.println("Closing file1");
        System.out.println("Closing file2");
        System.out.println("Closing file3");
        System.out.println("Closing file4");
    }

    public static void main(String[] args) {
//        strongReference();
//        weakReference();
//        softReference();
        phantomReference();
    }
}
