package com.urbankicks.config;

import com.urbankicks.entities.UserRegister;
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
    public UserDetailsServiceImpl(UserRegisterRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRegister user = userRepository.findByUsernameIgnoreCase(username);
        if (user != null)
            return user;
        else
            throw new UsernameNotFoundException("User not found");
    }
}
