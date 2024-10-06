package com.urbankicks.models;

import jakarta.validation.constraints.NotBlank;
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
public class RegisterPayload {

    @NotEmpty(message = "{msg.required.field}")
    @NotBlank(message = "{msg.not.blank.field}")
    @NotNull(message = "{msg.not.blank.field}")
    private String fname;

    @NotEmpty(message = "{msg.required.field}")
    @NotBlank(message = "{msg.not.blank.field}")
    @NotNull(message = "{msg.not.blank.field}")
    private String lname;

    @NotEmpty(message = "{msg.required.field}")
    @NotBlank(message = "{msg.not.blank.field}")
    @NotNull(message = "{msg.not.blank.field}")
    private String email;

    private String mobile;

    @NotEmpty(message = "{msg.required.field}")
    @NotBlank(message = "{msg.not.blank.field}")
    @NotNull(message = "{msg.not.blank.field}")
    private String password;

    @NotEmpty(message = "{msg.required.field}")
    @NotBlank(message = "{msg.not.blank.field}")
    @NotNull(message = "{msg.not.blank.field}")
    private String cpassword;

    @NotEmpty(message = "{msg.required.field}")
    @NotBlank(message = "{msg.not.blank.field}")
    @NotNull(message = "{msg.not.blank.field}")
    private String address;

    @NotNull(message = "{msg.not.blank.field}")
    private Integer stateId;

    @NotNull(message = "{msg.not.blank.field}")
    private Integer districtId;

    @NotEmpty(message = "{msg.required.field}")
    @NotBlank(message = "{msg.not.blank.field}")
    @NotNull(message = "{msg.not.blank.field}")
    private String city;

    @NotEmpty(message = "{msg.required.field}")
    @NotBlank(message = "{msg.not.blank.field}")
    @NotNull(message = "{msg.not.blank.field}")
    private String postal;
}
