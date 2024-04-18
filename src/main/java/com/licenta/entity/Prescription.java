package com.licenta.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name ="doctor_id")
    private DoctorProfile doctorProfile;

    @ManyToOne
    @JoinColumn(name="patient_id")
    private PatientProfile patientProfile;

    @ManyToMany(mappedBy = "prescriptions")
    private Set<Medicines> medicinesSet = new HashSet<>();
}
