package com.licenta.repository;

import com.licenta.entity.Appointment;
import com.licenta.entity.Prescription;
import com.licenta.entity.PrescriptionMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {
    Prescription findByAppointment(Appointment appointment);
}
