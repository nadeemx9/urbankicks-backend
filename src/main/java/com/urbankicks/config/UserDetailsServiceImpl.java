package com.urbankicks.config;

import com.urbankicks.entities.UserRegister;
import com.urbankicks.models.AuthenticatedUser;
import com.urbankicks.repositories.UserRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRegisterRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRegisterRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserRegister user = userRepository.findByEmailIgnoreCase(email);
        if (user != null)
            return new AuthenticatedUser(user); // Return the custom UserDetails
        else
            throw new UsernameNotFoundException("User not found");
    }
}
