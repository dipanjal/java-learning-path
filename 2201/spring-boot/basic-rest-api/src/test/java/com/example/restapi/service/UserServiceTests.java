package com.example.restapi.service;

import com.example.restapi.entity.UserEntity;
import com.example.restapi.exception.RecordNotFoundException;
import com.example.restapi.model.UserDTO;
import com.example.restapi.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("First Unit Test")
    public void test() {
        System.out.println("Test Code");
    }

    @Test
    @DisplayName("Get User Unit Test")
    public void getUsersTest() {
        when(userRepository.findAll())
                .thenReturn(generateUserEntityList());

        List<UserDTO> userDTOList = userService.getUsers();
        assertThat(userDTOList).isNotNull();
        assertThat(userDTOList).isNotEmpty();
    }

    private List<UserEntity> generateUserEntityList() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("Jhon Doe");
        userEntity.setEmail("jhon@email.com");

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(2L);
        userEntity2.setName("Erick");
        userEntity2.setEmail("erick@email.com");

        return List.of(
                userEntity,
                userEntity2
        );
    }

    @Test
    @DisplayName("Get user by id: RecordNotFound exception")
    public void getUserByIdTest_recordNotFound() {
        when(userRepository.getById(anyLong()))
                .thenReturn(null);
        assertThrows(RecordNotFoundException.class, () -> userService.getUserById(1L));
    }
}
