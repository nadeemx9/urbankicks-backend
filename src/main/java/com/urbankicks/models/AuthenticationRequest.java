package com.urbankicks.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    @NotEmpty(message = "Username should not be empty!")
    @NotNull(message = "Username should not be null!")
    private String username;

    @NotEmpty(message = "Password should not be empty!")
    @NotNull(message = "Password should not be null!")
    private String password;
}