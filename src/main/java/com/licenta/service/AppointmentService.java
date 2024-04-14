package com.licenta.service;

import com.licenta.entity.Appointment;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {
    Appointment makeAppointment(HttpServletRequest request);

    List<LocalTime> getAvailableHours(int doctorId, LocalDate localDate);

    void saveAppointment(Appointment appointment);
}
