package com.urbankicks.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    @NotEmpty(message = "{msg.required.field}")
    @NotBlank(message = "{msg.not.blank.field}")
    private String username;

    @NotEmpty(message = "{msg.required.field}")
    @NotBlank(message = "{msg.not.blank.field}")
    private String password;
}