package com.licenta.service;

import com.licenta.dto.EntityPaginationDto;
import com.licenta.entity.DoctorProfile;
import com.licenta.entity.HospitalProfile;

public interface HospitalService {
    EntityPaginationDto<HospitalProfile> getHospitalsPagination(int page, String search);

    void deleteHospital(int id);
}
