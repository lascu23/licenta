package com.licenta.service;

import com.licenta.dto.HospitalProfileDto;
import com.licenta.dto.PharmacyDto;
import com.licenta.dto.RegistrationAndRoleDto;
import com.licenta.dto.RegistrationDto;
import com.licenta.entity.User;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);
    User findByEmail(String email);
    //void saveHospitalOrPharmacy(RegistrationAndRoleDto registrationAndRoleDto);
    void saveHospitalUserAndProfile(HospitalProfileDto hospitalProfileDto);
    void savePharmacyUserAndProfile(PharmacyDto pharmacyDto);
}
