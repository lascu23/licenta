package com.licenta.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Table
@Entity
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "boolean default false")
    private boolean fulfilled;

    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

    @Column(name = "appointment_hour")
    private LocalTime appointmentHour;

    private String details;

    @ManyToOne
    @JoinColumn(name ="doctor_id")
    private DoctorProfile doctorProfile;

    @ManyToOne
    @JoinColumn(name="patient_id")
    private PatientProfile patientProfile;

    @ManyToOne
    @JoinColumn(name="review_id")
    private Review review;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    private Prescription prescription;

    public Appointment(DoctorProfile doctorProfile, PatientProfile patientProfile){
        this.doctorProfile = doctorProfile;
        this.patientProfile = patientProfile;
    }

    public Appointment(){}

}
