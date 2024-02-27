package com.licenta.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class MedicinePharmacyProfileId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicines medicine;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private PharmacyProfile pharmacyProfile;
}