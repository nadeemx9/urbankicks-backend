package com.urbankicks.controllers;

import com.urbankicks.models.AuthenticatedUser;
import com.urbankicks.repositories.UserRegisterRepository;
import com.urbankicks.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")

@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final UserRegisterRepository userRegisterRepository;

    @GetMapping("/logout")
    public void logout(@AuthenticationPrincipal AuthenticatedUser user) {
        authService.logout(userRegisterRepository.findByUserId(user.getUserId()));
    }

    @GetMapping("/me")
    public AuthenticatedUser getCurrentUser() {
        return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}