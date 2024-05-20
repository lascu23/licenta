package com.licenta.service;

import com.licenta.dto.AppointmentDto;
import com.licenta.dto.PrescriptionMedicineDtoForPatientCalendar;
import com.licenta.dto.ShowCalendarDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CalendarService {
    ShowCalendarDto createDoctorCalendar(int year, int month);

    List<AppointmentDto> getAppointments(int day, int currentMonth, int currentYear);

    ResponseEntity<?> markAppointmentAsFulfilled(int id);

    List<AppointmentDto> getPatientAppointmentsForCalendar();
    Map<AppointmentDto, List<PrescriptionMedicineDtoForPatientCalendar>> getPrescriptionsForPatient();
}
