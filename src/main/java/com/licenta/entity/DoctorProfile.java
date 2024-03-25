package com.licenta.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "doctor_profile")
public class DoctorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")

    private String lastName;

    private String birthday;

    @Column(name = "profile_picture")
    private byte[] profileImage;

    private String specialty;

    private String nationality;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private HospitalProfile hospitalProfile;

    @ManyToMany(mappedBy = "doctorProfileHashSet")
    private Set<Schedule> scheduleSet = new HashSet<>();

    @OneToMany(mappedBy = "doctorProfile", cascade = CascadeType.ALL, targetEntity = Appointment.class)
    private Set<Appointment> appointments;
}
