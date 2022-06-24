package com.example.restapi.service;

import com.example.restapi.aop.EnableLogging;
import com.example.restapi.entity.UserEntity;
import com.example.restapi.exception.RecordNotFoundException;
import com.example.restapi.model.UserDTO;
import com.example.restapi.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service //injectable bean
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
        UserEntity entity = userRepository.getById(id);
        if(Objects.isNull(entity)) {
            throw new RecordNotFoundException("No User found for this id");
        }
        return this.mapToDTO(entity);
    }

    private UserDTO mapToDTO(UserEntity entity) {
        return new UserDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail()
        );
    }
}
