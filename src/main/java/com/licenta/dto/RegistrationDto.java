package com.licenta.dto;

import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class RegistrationDto {
    private int id;
    @NotEmpty
    private String email;

    @NotEmpty
    @NotNull(message = "The password can not be null!")
    @NotBlank(message = "The password can not be empty or filled with spaces!")
    private String password;
}
