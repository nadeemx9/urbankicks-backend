package com.urbankicks.services;

import com.urbankicks.repositories.UserRegisterRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private UserRegisterRepository userRegisterRepository;
}
