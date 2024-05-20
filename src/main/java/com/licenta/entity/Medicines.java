package com.licenta.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Table
@Entity
@Getter
@Setter
public class Medicines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "pharmaceutical_form")
    private String pharmaceuticalForm;

    private String concentration;

    private String packaging;

    private String validity;

    private Double price;

    private String indications;

    @Column(name = "side_effects")
    private String sideEffects;

    @Column(name = "prescription_required")
    private boolean prescriptionRequired;

    private String volume;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "pharmacy_medicine",
            joinColumns = {@JoinColumn(name = "medicine_id")},
            inverseJoinColumns = {@JoinColumn(name = "pharmacy_id")}
    )
    private Set<PharmacyProfile> pharmacyProfileSet = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "prescription_medicine",
            joinColumns = {@JoinColumn(name = "medicine_id")},
            inverseJoinColumns = {@JoinColumn(name = "prescription_id")}
    )
    private Set<Prescription> prescriptions = new HashSet<>();
}
