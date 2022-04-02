package com.dipanjal.batch1.annotation;

@MyService(value = "userService")
public class UserService {

    public void print() {
        System.out.println("Printing from: "+ UserService.class.getSimpleName());
    }
}
