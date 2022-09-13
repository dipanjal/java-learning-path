package com.example.restapi;

import com.example.restapi.entity.RoleEntity;
import com.example.restapi.entity.UserEntity;
import com.example.restapi.enums.UserRoleEnum;
import com.example.restapi.repository.RoleRepository;
import com.example.restapi.repository.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import static com.example.restapi.enums.UserRoleEnum.ADMIN;
import static com.example.restapi.enums.UserRoleEnum.USER;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Application started....");
        this.generateUsers()
                .forEach(userEntity -> log.info("{}", userEntity.getUserName()));
        this.createUserWithMultipleRoles(USER, ADMIN).ifPresent(
                userEntity -> log.info("{}", userEntity.getUserName())
        );
    }

    private List<RoleEntity> generateRoles() {
        List<RoleEntity> roleEntities = roleRepository.findAll();

        if(!roleEntities.isEmpty())
            return roleEntities;

        return roleRepository.saveAll(
                List.of(
                        new RoleEntity(USER),
                        new RoleEntity(ADMIN)
                )
        );
    }

    private List<UserEntity> generateUsers() {
        List<RoleEntity> roleEntities = generateRoles();

        List<UserEntity> existingUsers = userRepository.findAll();
        if(!existingUsers.isEmpty())
            return existingUsers;

        List<UserEntity> newUserEntityList = new ArrayList<>();

        findByRoleEnum(roleEntities, USER)
                .ifPresent(roleEntity -> newUserEntityList.addAll(
                        List.of(
                                new UserEntity("jhon", "jhon", "Jhon Doe", null, roleEntity),
                                new UserEntity("erik", "erik", "Erik Clapton", null, roleEntity)
                        )
                ));
        findByRoleEnum(roleEntities, ADMIN)
                .ifPresent(roleEntity -> newUserEntityList.addAll(
                        List.of(
                                new UserEntity("admin", "admin", "Admin 1", null, roleEntity),
                                new UserEntity("admin2", "admin2", "Admin 2", null, roleEntity)
                        )
                ));
        return userRepository.saveAll(newUserEntityList);
    }

    private Optional<UserEntity> createUserWithMultipleRoles(UserRoleEnum... roles) {
        Set<Long> roleIds = Arrays.stream(roles)
                .map(UserRoleEnum::getId)
                .collect(Collectors.toSet());

        Set<RoleEntity> availableRoles = roleRepository.findByIdIn(roleIds);
        List<UserEntity> userWithMultipleRoles = getMultiRoleUsers(availableRoles);

        return userWithMultipleRoles.isEmpty()
                ? Optional.of(userRepository.save(
                new UserEntity("admin3", "admin3", "", "", availableRoles)
        ))
                : Optional.empty();
    }

    private List<UserEntity> getMultiRoleUsers(Set<RoleEntity> availableRoles) {
        return userRepository.findByRolesIn(availableRoles).stream()
                .filter(uEntity -> uEntity.getRoles().size() > 1)
                .collect(Collectors.toList());
    }

    private Optional<RoleEntity> findByRoleEnum(List<RoleEntity> roleEntities, UserRoleEnum roleEnum) {
        return roleEntities.stream()
                .filter(roleEntity -> roleEntity.getName().equals(roleEnum.name()))
                .findFirst();

    }
}
