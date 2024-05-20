package com.licenta.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrescriptionMedicineDtoForPatientCalendar {
    private String medicineName;
    private String details;

    public PrescriptionMedicineDtoForPatientCalendar(String medicineName, String details) {
        this.medicineName = medicineName;
        this.details = details;
    }

}
