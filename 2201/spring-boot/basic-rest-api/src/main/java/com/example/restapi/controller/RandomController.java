package com.example.restapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RandomController {

    public ResponseEntity<?> rand() {
        return ResponseEntity.ok(UUID.randomUUID().toString());
    }
}
