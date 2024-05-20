package com.licenta.entity;

import com.licenta.common.PersonalData;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "doctor_profile")
public class DoctorProfile extends PersonalData {
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
