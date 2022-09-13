package com.example.restapi.repository;

import com.example.restapi.entity.RoleEntity;
import com.example.restapi.entity.UserEntity;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName);
    Set<UserEntity> findByRolesIn(Set<RoleEntity> roles);
}
