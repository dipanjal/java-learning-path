package com.dipanjal.batch1.annotation;

@MyController(value = "userService")
public class UserController {

    public void print() {
        System.out.println("Printing from: "+UserController.class.getSimpleName());
    }
}
