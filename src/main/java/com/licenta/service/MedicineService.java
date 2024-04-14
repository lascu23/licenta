package com.licenta.service;

import com.licenta.dto.EntityPaginationDto;
import com.licenta.entity.DoctorProfile;
import com.licenta.entity.Medicines;

public interface MedicineService {
    EntityPaginationDto<Medicines> getMedicinesPagination(int page, String search);

    void deleteMedicine(int id);

    void addMedicineToPharmacy(int id, int quantity);

    EntityPaginationDto<Medicines> seeMedicinesForOnePharmacy(String name, String address, int page, String search);
}
