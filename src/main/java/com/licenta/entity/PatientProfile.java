package com.licenta.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.parameters.P;

import java.util.Set;

@Data
@Entity
@Table(name = "patient_profile")
public class PatientProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String sex;

    private String birthday;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "patientProfile", cascade = CascadeType.ALL, targetEntity = Appointment.class)
    private Set<Appointment> appointments;

    @OneToMany(mappedBy = "patientProfile", cascade = CascadeType.ALL, targetEntity = Prescription.class)
    private Set<Prescription> prescriptions;
}
