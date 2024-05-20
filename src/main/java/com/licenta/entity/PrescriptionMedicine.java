package com.licenta.entity;

import com.licenta.EmbeddedKeys.PrescriptionMedicineId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "prescription_medicine")
@Getter
@Setter
public class PrescriptionMedicine {
    @EmbeddedId
    private PrescriptionMedicineId id;

    private String details;

    @MapsId("medicine")
    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicines medicines;

    @MapsId("prescription")
    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;
}
