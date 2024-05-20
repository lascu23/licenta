package com.licenta.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.print.Doc;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Table
@Entity
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "start_hour")
    private LocalTime startHour;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "end_hour")
    private LocalTime endHour;

    private String day;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "pharmacy_schedule",
            joinColumns = {@JoinColumn(name = "schedule_id")},
            inverseJoinColumns = {@JoinColumn(name = "pharmacy_id")}
    )
    private Set<PharmacyProfile> pharmacyProfileSet = new HashSet<>();


    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "hospital_schedule",
            joinColumns = {@JoinColumn(name = "schedule_id")},
            inverseJoinColumns = {@JoinColumn(name = "hospital_id")}
    )
    private Set<HospitalProfile> hospitalProfilesSet = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "doctor_schedule",
            joinColumns = {@JoinColumn(name = "schedule_id")},
            inverseJoinColumns = {@JoinColumn(name = "doctor_id")}
    )
    private Set<DoctorProfile> doctorProfileHashSet = new HashSet<>();
}
