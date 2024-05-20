package com.licenta.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
public class AppointmentDto implements Comparable<AppointmentDto>{
    private LocalDate appointmentDate;
    private LocalTime appointmentHour;
    private String patientLastName;
    private String patientFirstName;
    private String details;
    private boolean fulfilled;
    private int id;
    private String doctorLastName;
    private String doctorFirstName;

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

    @Override
    public int compareTo(AppointmentDto other) {
        int dataComparison = this.appointmentDate.compareTo(other.getAppointmentDate());
        if (dataComparison != 0) {
            return dataComparison;
        }
        return this.appointmentHour.compareTo(other.getAppointmentHour());
    }
}
