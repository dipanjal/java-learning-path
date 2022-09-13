package com.example.restapi.service.auth;

import com.example.restapi.model.request.auth.AuthenticationRequest;
import com.example.restapi.model.response.auth.TokenResponse;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public interface AuthService {
    TokenResponse authenticate(AuthenticationRequest request);
    UserDetails validateToken(String token);
}
