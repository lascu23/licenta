package com.licenta.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, targetEntity = PharmacyProfile.class)
    private Set<PharmacyProfile> pharmacyProfileSet;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, targetEntity = PatientProfile.class)
    private Set<PatientProfile> patientProfileSet;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, targetEntity = DoctorProfile.class)
    private Set<DoctorProfile> doctorProfileSet;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, targetEntity = HospitalProfile.class)
    private Set<HospitalProfile> hospitalProfileSet;

}
