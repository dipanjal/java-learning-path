package com.example.restapi.controller;

import com.example.restapi.entity.UserEntity;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/admin")
    public ResponseEntity<List<?>> getAllAdmins() {
        UserEntity user = new UserEntity();
        user.setId(10L);
        user.setEmail("admin@site.com");
        user.setName("Admin1");
        return ResponseEntity.ok(
                List.of(user)
        );
    }
}
