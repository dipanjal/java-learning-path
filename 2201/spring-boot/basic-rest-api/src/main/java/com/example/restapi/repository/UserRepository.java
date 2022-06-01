package com.example.restapi.repository;

import com.example.restapi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity getUserEntityByEmail(String email);
}
