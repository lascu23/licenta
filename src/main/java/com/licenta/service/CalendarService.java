package com.licenta.service;

import com.licenta.dto.ShowCalendarDto;
import com.licenta.entity.Appointment;
import com.licenta.entity.PrescriptionMedicine;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CalendarService {
    ShowCalendarDto createDoctorCalendar(int year, int month);

    List<Appointment> getAppointments(int day, int currentMonth, int currentYear);

    ResponseEntity<?> markAppointmentAsFulfilled(int id);

    List<Appointment> getPatientAppointmentsForCalendar();
    List<PrescriptionMedicine> getPrescriptionsForPatient();
}
