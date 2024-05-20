package com.licenta.repository;

import com.licenta.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findAllByDoctorProfileIdAndAppointmentDate(int doctorProfileId, LocalDate date);
    List<Appointment> findAllByPatientProfileIdOrderByAppointmentDate(int patientId);
    List<Appointment> findAllByDoctorProfileId(int doctorId);
    List<Appointment> findAllByPatientProfileIdAndDoctorProfileId(int patientId, int doctorId);
    Appointment findById(int id);


}
