package com.licenta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class DoctorDto {
    @NotEmpty
    @NotNull
    @NotBlank
    private String email;
    @NotEmpty
    @NotNull
    @NotBlank
    private String password;
    @NotEmpty
    @NotNull
    @NotBlank
    private String firstName;
    @NotEmpty
    @NotNull
    @NotBlank
    private String lastName;
    @NotEmpty
    @NotNull
    @NotBlank
    private String birthday;

    private MultipartFile profileImage;
    @NotEmpty
    @NotNull
    @NotBlank
    private String specialty;
    @NotEmpty
    @NotNull
    @NotBlank
    private String nationality;

}
