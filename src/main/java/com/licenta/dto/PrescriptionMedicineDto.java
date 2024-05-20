package com.licenta.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PrescriptionMedicineDto {
    List<String> medicineNames;
    List<String> details;
}
