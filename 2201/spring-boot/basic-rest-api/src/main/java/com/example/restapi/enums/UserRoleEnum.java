package com.example.restapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRoleEnum {
    USER(1),
    ADMIN(2);

    private final long id;

}
