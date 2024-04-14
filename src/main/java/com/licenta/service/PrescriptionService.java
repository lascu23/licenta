package com.licenta.service;

import com.licenta.dto.PrescriptionMedicineDto;
import org.springframework.web.bind.annotation.RequestParam;

public interface PrescriptionService {
    PrescriptionMedicineDto createPrescription();

    void savePrescription(PrescriptionMedicineDto prescriptionMedicineDto, int doctorId,  int patientId);
}
