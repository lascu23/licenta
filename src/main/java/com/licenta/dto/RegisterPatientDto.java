package com.licenta.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterPatientDto {
    @NotEmpty
    private String email;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String sex;

    @NotEmpty
    private String birthday;

    @NotEmpty
    private String phoneNumber;
}
