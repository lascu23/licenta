package com.licenta.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Table
@Entity
@Data
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

    public Appointment(DoctorProfile doctorProfile, PatientProfile patientProfile){
        this.doctorProfile = doctorProfile;
        this.patientProfile = patientProfile;
    }

    public Appointment(){}


}
