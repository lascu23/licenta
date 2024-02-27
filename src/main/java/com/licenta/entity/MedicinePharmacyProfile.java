package com.licenta.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;



@Entity
@Table(name = "pharmacy_medicine")
@Data
public class MedicinePharmacyProfile {
    @EmbeddedId
    private MedicinePharmacyProfileId id;

    private int quantity;

    @MapsId("medicine")
    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicines medicines;

    @MapsId("pharmacyProfile")
    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private PharmacyProfile pharmacyProfile;
}
