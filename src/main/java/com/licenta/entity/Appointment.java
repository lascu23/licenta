package com.licenta.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table
@Entity
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "appointment_date")
    private String appointmentDate;

    @Column(name = "appointment_hour")
    private String appointmentHour;

    private String details;

    @ManyToOne
    @JoinColumn(name ="doctor_id")
    private DoctorProfile doctorProfile;

    @ManyToOne
    @JoinColumn(name="patient_id")
    private PatientProfile patientProfile;
}
