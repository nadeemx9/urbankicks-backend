package com.urbankicks.services;

import com.urbankicks.config.jwt.JwtService;
import com.urbankicks.entities.UserRegister;
import com.urbankicks.models.*;
import com.urbankicks.repositories.DistrictRepository;
import com.urbankicks.repositories.StateRepository;
import com.urbankicks.repositories.UserRegisterRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

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

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate the user using the provided credentials
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Extract authenticated user details
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) auth.getPrincipal();
        Integer userId = authenticatedUser.getUserId();

        // Retrieve user from repository
        Optional<UserRegister> optionalUser = userRegisterRepository.findById(userId);
        if (optionalUser.isPresent()) {
            UserRegister user = optionalUser.get();

            // Update user's last login information
            user.setIsLoggedOut(false);
            user.setLastLoggedIn(LocalDateTime.now());
            userRegisterRepository.save(user);

            // Prepare JWT claims
            var claims = new HashMap<String, Object>();
            claims.put("fullName", user.getFullName());

            // Generate JWT token
            var jwtToken = jwtService.generateToken(claims, authenticatedUser);

            // Build and return the authentication response
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            // Handle case where user is not found (optional)
            throw new UsernameNotFoundException("User not found");
        }
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
