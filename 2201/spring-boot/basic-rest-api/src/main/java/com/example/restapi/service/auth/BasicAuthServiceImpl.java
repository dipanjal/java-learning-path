package com.example.restapi.service.auth;

import com.example.restapi.model.request.auth.AuthenticationRequest;
import com.example.restapi.model.response.auth.TokenResponse;
import com.example.restapi.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Service
public class BasicAuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public BasicAuthServiceImpl(@Qualifier("appUserDetailsService") UserDetailsService userDetailsService,
                                AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public TokenResponse authenticate(AuthenticationRequest request) {
        try{
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            );
            authenticationManager.authenticate(auth);
        }catch (BadCredentialsException e) {
            throw new BadCredentialsException("Bad credential", e);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = JWTUtils.generateToken(userDetails.getUsername());
        return new TokenResponse(token);
    }

    @Override
    public UserDetails validateToken(String token) {
        if(JWTUtils.isTokenInvalidOrExpired(token)) {
            throw new RuntimeException("Invalid token");
//            throw new AuthException(getMessage("validation.token.expired.or.invalid.message"));
        }
        return userDetailsService.loadUserByUsername(
                JWTUtils.extractUserName(token)
        );
    }
}
