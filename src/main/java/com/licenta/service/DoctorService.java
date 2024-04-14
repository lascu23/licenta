package com.licenta.service;

import com.licenta.dto.EntityPaginationDto;
import com.licenta.entity.DoctorProfile;

public interface DoctorService {
    EntityPaginationDto<DoctorProfile> getDoctorsPagination(int page, String search);

    void deleteDoctor(int id);
}
