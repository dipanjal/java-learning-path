package com.example.restapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @GetMapping("/pay")
    public ResponseEntity<?> pay() {
        throw new RuntimeException("Payment Failed due to exception");
    }
}
