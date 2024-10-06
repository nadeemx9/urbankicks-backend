package com.urbankicks.services;

import com.urbankicks.config.jwt.JwtService;
import com.urbankicks.entities.UserRegister;
import com.urbankicks.models.APIResponse;
import com.urbankicks.models.AuthenticationRequest;
import com.urbankicks.models.AuthenticationResponse;
import com.urbankicks.models.RegisterPayload;
import com.urbankicks.repositories.DistrictRepository;
import com.urbankicks.repositories.StateRepository;
import com.urbankicks.repositories.UserRegisterRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.ssl.SslAutoConfiguration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRegisterRepository userRegisterRepository;
    private final PasswordEncoder passwordEncoder;
    private final StateRepository stateRepository;
    private final DistrictRepository districtRepository;
    private final SslAutoConfiguration sslAutoConfiguration;

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

    public APIResponse register(RegisterPayload payload) {
        try {
            UserRegister user = UserRegister.builder()
                    .email(payload.getEmail())
                    .password(passwordEncoder.encode(payload.getPassword()))
                    .firstname(payload.getFname())
                    .lastname(payload.getLname())
                    .address(payload.getAddress())
                    .mobile(payload.getMobile() != null ? payload.getMobile() : null)
                    .role(UserRegister.Role.CUSTOMER)
                    .state(stateRepository.findByStateId(payload.getStateId()))
                    .district(districtRepository.findByDistrictId(payload.getDistrictId()))
                    .createdAt(LocalDateTime.now())
                    .isLoggedOut(true)
                    .build();
            userRegisterRepository.save(user);

            return APIResponse.builder()
                    .status(1)
                    .respMsg("Register Success")
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return APIResponse.builder()
                    .status(2)
                    .respMsg(e.getMessage())
                    .build();
        }
    }

    public void logout(UserRegister user) {
        user.setIsLoggedOut(true);
        userRegisterRepository.save(user);
    }
}
