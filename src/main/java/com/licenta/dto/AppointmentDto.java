package com.licenta.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentDto {
    private LocalDate appointmentDate;
    private LocalTime appointmentHour;
    private String patientLastName;
    private String patientFirstName;
    private String details;
    private boolean fulfilled;

    public AppointmentDto() {
    }

    public AppointmentDto(LocalDate appointmentDate, LocalTime appointmentHour, String patientLastName, String patientFirstName, String details, boolean fulfilled) {
        this.appointmentDate = appointmentDate;
        this.appointmentHour = appointmentHour;
        this.patientLastName = patientLastName;
        this.patientFirstName = patientFirstName;
        this.details = details;
        this.fulfilled = fulfilled;
    }
}
