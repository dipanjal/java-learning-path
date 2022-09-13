package com.example.restapi.controller;

import com.example.restapi.model.request.auth.AuthenticationRequest;
import com.example.restapi.service.auth.AuthService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@RestController
@RequiredArgsConstructor
public class AccessController {

    private final AuthService authService;

    @PostMapping("/access/token")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
