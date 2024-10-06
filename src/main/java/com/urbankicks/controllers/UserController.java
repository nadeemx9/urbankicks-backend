package com.urbankicks.controllers;

import com.urbankicks.entities.UserRegister;
import com.urbankicks.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")

@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @GetMapping("/logout")
    public void logout(@AuthenticationPrincipal UserRegister user) {
        authService.logout(user);
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test(@AuthenticationPrincipal UserRegister user) {
        return new ResponseEntity<>("test", HttpStatus.OK);
    }
}