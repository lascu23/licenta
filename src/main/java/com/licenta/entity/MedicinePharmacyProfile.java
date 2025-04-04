package com.licenta.entity;

import com.licenta.EmbeddedKeys.MedicinePharmacyProfileId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "pharmacy_medicine")
@Getter
@Setter
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
