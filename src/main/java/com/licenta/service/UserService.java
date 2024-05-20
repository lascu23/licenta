package com.licenta.service;

import com.licenta.dto.*;
import com.licenta.entity.User;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);
    User findByEmail(String email);

    void saveHospitalUserAndProfile(HospitalProfileDto hospitalProfileDto);
    void savePharmacyUserAndProfile(PharmacyDto pharmacyDto);
    void saveDoctorUserAndProfile(DoctorDto doctorDto);

    User getAuthenticationUser();
}
