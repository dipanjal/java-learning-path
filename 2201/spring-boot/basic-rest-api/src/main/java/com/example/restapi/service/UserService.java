package com.example.restapi.service;

import com.example.restapi.aop.EnableLogging;
import com.example.restapi.exception.InvalidArgumentException;
import com.example.restapi.exception.RecordNotFoundException;
import com.example.restapi.model.UserDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service //injectable bean
public class UserService {

    private final List<UserDTO> userDTOList;

    public UserService() {
        this.userDTOList = new ArrayList<>();
        userDTOList.add(new UserDTO(1,"Henry", 24));
        userDTOList.add(new UserDTO(2,"Sam", 27));
    }

    @EnableLogging
    public List<UserDTO> getUsers() throws InterruptedException {
        Thread.sleep(30000);
        return userDTOList;
    }


    public UserDTO getUserById(long id) throws RecordNotFoundException {
        if(id < 1)
            throw new InvalidArgumentException("Invalid User id");

        for(UserDTO userDTO : userDTOList) {
            if(userDTO.getId() == id) {
                return userDTO;
            }
        }
        throw new RecordNotFoundException("User not found");
    }
}
