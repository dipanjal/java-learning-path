package com.example.restapi.service;

import com.example.restapi.aop.EnableLogging;
import com.example.restapi.exception.InvalidArgumentException;
import com.example.restapi.exception.RecordNotFoundException;
import com.example.restapi.model.UserDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    private final List<UserDTO> userDTOList;
    private final RestTemplate restTemplate;
    @Value("${user-service.baseurl}")
    private String userServiceBaseUrl;

    public UserService(final RestTemplate restTemplate) {
        this.userDTOList = new ArrayList<>();
        userDTOList.add(new UserDTO(1,"Henry", 24));
        userDTOList.add(new UserDTO(2,"Sam", 27));
        this.restTemplate = restTemplate;
    }

    @EnableLogging
    public List<UserDTO> getUsers() throws InterruptedException {
//        Thread.sleep(30000);
        return userDTOList;
    }


    public UserDTO getUserById(long id) throws RecordNotFoundException {
        if(id < 1)
            throw new InvalidArgumentException("Invalid User id");

        ParameterizedTypeReference<List<UserDTO>> typeReference = new ParameterizedTypeReference<>() {};

        ResponseEntity <List<UserDTO>> response = restTemplate.exchange(
                userServiceBaseUrl+"/users",
                HttpMethod.GET,
                null,
                typeReference
        );

        List<UserDTO> userDTOList = response.getBody();

        for(UserDTO userDTO : userDTOList) {
            if(userDTO.getId() == id) {
                return userDTO;
            }
        }
        throw new RecordNotFoundException("User not found");
    }
}
