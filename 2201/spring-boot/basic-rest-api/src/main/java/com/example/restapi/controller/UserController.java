package com.example.restapi.controller;

import com.example.restapi.model.UserDTO;
import com.example.restapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //another injectable bean
public class UserController {

    @Autowired //inject here
    private UserService userService;

    @GetMapping("/users")
    public List<UserDTO> fetchUsers() {
//        UserService userService = new UserService();
        System.out.println(userService);
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> fetchUser(@PathVariable long id) {
        try {
            UserDTO userDTO = userService.getUserById(id);
            return ResponseEntity
                    .ok()
                    .header("message", "User found")
                    .body(userDTO);
        }
        catch (NullPointerException e) {
            return ResponseEntity.internalServerError()
                    .header("message", "Internal Server Error!!")
                    .build();
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header("message", e.getMessage())
                    .build();
        }
    }
//
//    @PostMapping(value="/users", consumes = )
//    public UserDTO registerNewUser(@RequestBody UserRequest userRequest) {
//        //
//    }

}
