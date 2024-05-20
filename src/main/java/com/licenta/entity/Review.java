package com.licenta.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int grade;

    @Column(name = "description_grade")
    private String descriptionGrade;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, targetEntity = Appointment.class)
    private Set<Appointment> appointments;
}
