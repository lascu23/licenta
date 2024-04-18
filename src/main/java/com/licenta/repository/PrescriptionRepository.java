package com.licenta.repository;

import com.licenta.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {
    List<Prescription> findAllByPatientProfileId(int patientId);
}
