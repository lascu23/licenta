package com.licenta.repository;

import com.licenta.entity.Prescription;
import com.licenta.entity.PrescriptionMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrescriptionMedicineRepository extends JpaRepository<PrescriptionMedicine, Integer> {
    @Query("SELECT pm FROM PrescriptionMedicine pm WHERE pm.prescription.id IN :prescriptionIds")
    List<PrescriptionMedicine> findByPrescriptionIds(List<Integer> prescriptionIds);

    List<PrescriptionMedicine> findByPrescription(Prescription prescription);
}
