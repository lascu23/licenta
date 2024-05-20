package com.licenta.entity;

import com.licenta.common.PersonalData;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "patient_profile")
public class PatientProfile extends PersonalData {

    private String sex;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "patientProfile", cascade = CascadeType.ALL, targetEntity = Appointment.class)
    private Set<Appointment> appointments;
}
