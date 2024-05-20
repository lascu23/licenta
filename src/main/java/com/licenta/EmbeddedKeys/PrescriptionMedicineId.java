package com.licenta.EmbeddedKeys;

import com.licenta.entity.Medicines;
import com.licenta.entity.Prescription;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class PrescriptionMedicineId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicines medicine;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;
}
