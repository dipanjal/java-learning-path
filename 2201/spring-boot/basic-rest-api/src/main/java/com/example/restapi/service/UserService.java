package com.example.restapi.service;

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

    public List<UserDTO> getUsers() {
        return userDTOList;
    }


    public UserDTO getUserById(long id) throws Exception {
        for(UserDTO userDTO : userDTOList) {
            if(userDTO.getId() == id) {
                return userDTO;
            }
        }
        throw new Exception("User not found");
    }
}
