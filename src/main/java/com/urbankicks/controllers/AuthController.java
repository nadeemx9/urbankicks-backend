package com.urbankicks.controllers;

import com.urbankicks.models.APIResponse;
import com.urbankicks.models.AuthenticationRequest;
import com.urbankicks.models.AuthenticationResponse;
import com.urbankicks.models.RegisterPayload;
import com.urbankicks.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")

@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponse> register(@RequestBody @Valid RegisterPayload payload) {
        return new ResponseEntity<>(authService.register(payload), HttpStatus.OK);
    }
}