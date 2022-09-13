package com.example.restapi.model.request.auth;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Getter
@Setter
public class AuthenticationRequest {
    @NotBlank(message = "username can't be blank")
    private String username;
    @NotBlank(message = "password can't be blank")
    private String password;
}
