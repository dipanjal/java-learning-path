package com.example.restapi.controller;

import com.example.restapi.aop.EnableLogging;
import com.example.restapi.model.UserDTO;
import com.example.restapi.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @EnableLogging
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> fetchUsers() throws InterruptedException {
        return ResponseEntity.ok(userService.getUsers());
    }

    @EnableLogging
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> fetchUser(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @EnableLogging
    @GetMapping("/users/rand")
    public ResponseEntity<?> aRandomHandler() {
        throw new RuntimeException("Exception Thrown from Controller");
    }

}
