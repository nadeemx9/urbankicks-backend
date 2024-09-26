package com.urbankicks.repositories;

import com.urbankicks.entities.UserRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRegisterRepository extends JpaRepository<UserRegister, Integer> {
    UserRegister findByUsernameIgnoreCase(String username);
}
