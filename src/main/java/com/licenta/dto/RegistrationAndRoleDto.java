package com.licenta.dto;

import com.licenta.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistrationAndRoleDto {
    private int id;
    @NotEmpty
    @NotNull
    @NotBlank
    private String email;

    @NotEmpty
    @NotNull(message = "The password can not be null!")
    @NotBlank(message = "The password can not be empty or filled with spaces!")
    private String password;

    @NotEmpty
    @NotNull
    @NotBlank
    private String role;
}
