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
public class HospitalProfileDto {
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
    private String name;
    @NotEmpty
    @NotNull
    @NotBlank
    private String address;
    @NotEmpty
    @NotNull
    @NotBlank
    private String city;
    @NotEmpty
    @NotNull
    @NotBlank
    private String country;

    private MultipartFile profileImage;

}
