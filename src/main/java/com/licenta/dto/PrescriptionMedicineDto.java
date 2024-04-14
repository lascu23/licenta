package com.licenta.dto;

import lombok.Data;

import java.util.List;

@Data
public class PrescriptionMedicineDto {
    List<String> medicineNames;
    List<String> details;
}
