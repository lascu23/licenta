package com.licenta.service;

import com.licenta.dto.EntityPaginationDto;
import com.licenta.entity.DoctorProfile;
import com.licenta.entity.PharmacyProfile;

public interface PharmacyService {
    EntityPaginationDto<PharmacyProfile> getPharmacyPagination(int page, String search, String medicine);

    void deletePharmacy(int id);
}
