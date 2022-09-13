package com.example.restapi.service;

import com.example.restapi.aop.EnableLogging;
import com.example.restapi.entity.UserEntity;
import com.example.restapi.exception.InvalidArgumentException;
import com.example.restapi.exception.RecordNotFoundException;
import com.example.restapi.model.UserDTO;
import com.example.restapi.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    @Value("${user-service.baseurl}")
    private String userServiceBaseUrl;


    @EnableLogging
    public List<UserDTO> getUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDTO> dtoList = new ArrayList<>();
        for(UserEntity entity : userEntities) {
            dtoList.add(this.mapToDTO(entity));
        }
        return dtoList;
    }


    public UserDTO getUserById(long id) throws RecordNotFoundException {
        return this.mapToDTO(
                userRepository.findById(id)
                        .orElseThrow(() -> new RecordNotFoundException("No User found for this id"))
        );
    }

    public UserDTO fetchUserById(long id) throws RecordNotFoundException {
        if(id < 1)
            throw new InvalidArgumentException("Invalid User id");

        ResponseEntity <List<UserDTO>> response = restTemplate.exchange(
                userServiceBaseUrl+"/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        List<UserDTO> userDTOList = response.getBody();
        if(CollectionUtils.isEmpty(userDTOList))
            throw new RecordNotFoundException("User not found");

        return userDTOList.stream()
                .filter(userDTO -> userDTO.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
    }

    private UserDTO mapToDTO(UserEntity entity) {
        return new UserDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail()
        );
    }
}
