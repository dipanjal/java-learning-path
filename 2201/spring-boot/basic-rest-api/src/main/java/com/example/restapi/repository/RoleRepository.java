package com.example.restapi.repository;

import com.example.restapi.entity.RoleEntity;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Set<RoleEntity> findByIdIn(Set<Long> ids);
}
