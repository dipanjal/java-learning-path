package com.dipanjal.batch1.annotation;

@MyController(value = "homeController")
public class HomeController {

    public void print() {
        System.out.println("Printing from: "+ HomeController.class.getSimpleName());
    }
}
