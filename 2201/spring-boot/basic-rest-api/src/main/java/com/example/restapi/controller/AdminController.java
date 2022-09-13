package com.example.restapi.controller;

import com.example.restapi.config.CurrentUser;
import com.example.restapi.entity.UserEntity;
import java.security.Principal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/admin")
    public ResponseEntity<List<?>> getAllAdmins(Authentication authentication) {
        CurrentUser userDetails = (CurrentUser) authentication.getPrincipal();
        UserEntity user = new UserEntity();
        user.setId(10L);
        user.setEmail("admin@site.com");
        user.setName("Admin1");
//        user.setRoles();
        return ResponseEntity.ok(
                List.of(user)
        );
    }
}
