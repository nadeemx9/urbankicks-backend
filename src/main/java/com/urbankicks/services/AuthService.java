package com.urbankicks.services;

import com.urbankicks.config.jwt.JwtService;
import com.urbankicks.entities.UserRegister;
import com.urbankicks.models.AuthenticationRequest;
import com.urbankicks.models.AuthenticationResponse;
import com.urbankicks.repositories.UserRegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRegisterRepository userRegisterRepository;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((UserRegister) auth.getPrincipal());
        user.setIsLoggedOut(false);
        user.setLastLoggedIn(LocalDateTime.now());

        claims.put("fullName", user.getFullName());

        userRegisterRepository.save(user);

        var jwtToken = jwtService.generateToken(claims, (UserRegister) auth.getPrincipal());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void logout(UserRegister user) {
        user.setIsLoggedOut(true);
        userRegisterRepository.save(user);
    }
}
