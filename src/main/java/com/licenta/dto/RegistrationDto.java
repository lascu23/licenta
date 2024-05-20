package com.licenta.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegistrationDto {
    private int id;
    @NotEmpty
    @NotNull
    private String email;

    @NotEmpty
    @NotNull(message = "The password can not be null!")
    @NotBlank(message = "The password can not be empty or filled with spaces!")
    @Size(min = 8, message = "The password must have at least 8 characters! ")
    private String password;

    @NotEmpty
    @NotNull(message = "The password can not be null!")
    @NotBlank(message = "The password can not be empty or filled with spaces!")
    private String secondPassword;

}
