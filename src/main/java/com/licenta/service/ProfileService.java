package com.licenta.service;

import com.licenta.dto.ScheduleDtoUserAndPhotoDto;
import com.licenta.dto.ScheduleUserAndPhotoDto;
import com.licenta.entity.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

import javax.print.Doc;
import java.util.ArrayList;

public interface ProfileService {
    PatientProfile showLoggedInPatientProfile();


    void savePatientProfile(PatientProfile patientProfile);

    void deleteAccount(String email, HttpServletRequest request, HttpServletResponse response);

    ScheduleDtoUserAndPhotoDto<?> getProfile(ArrayList<Schedule> schedules);

    ScheduleUserAndPhotoDto<HospitalProfile> getHospitalProfileFromUserPerspective(String hospitalName, @RequestParam String address);
    ScheduleUserAndPhotoDto<DoctorProfile> getDoctorProfileFromUserPerspective(String firstName, String lastName, String specialty);
    ScheduleUserAndPhotoDto<PharmacyProfile> getPharmacyProfileFromUserPerspective(String name, String address);
}
