package com.licenta.controller;


import com.licenta.dto.ScheduleDtoUserAndPhotoDto;
import com.licenta.dto.ScheduleUserAndPhotoDto;
import com.licenta.entity.*;
import com.licenta.repository.*;
import com.licenta.service.ProfileServiceImpl;
import com.licenta.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfilController {
    final
    ProfileServiceImpl profileService;

    private final UserServiceImpl userService;

    private final AppointmentRepository appointmentRepository;
    private final ReviewRepository reviewRepository;

    public ProfilController(ProfileServiceImpl profileService, UserServiceImpl userService, AppointmentRepository appointmentRepository, ReviewRepository reviewRepository) {
        this.profileService = profileService;
        this.userService = userService;
        this.appointmentRepository = appointmentRepository;
        this.reviewRepository = reviewRepository;
    }


    @GetMapping("/profilePatient")
    public String getProfilePatient(Model model){

        model.addAttribute("user", profileService.showLoggedInPatientProfile());
        model.addAttribute("userEmail", userService.getAuthenticationUser().getEmail());
        return "createProfilePatient";
    }

    @PostMapping("/profilePatient/save")
    public String saveProfilePatient(@ModelAttribute("user") PatientProfile patientProfile){
        profileService.savePatientProfile(patientProfile);
        return "redirect:/home";
    }

    @GetMapping("/deleteAccount")
    public String deleteAccount(@RequestParam("userEmail") String email, HttpServletRequest request, HttpServletResponse response){
        profileService.deleteAccount(email, request, response);
        return "redirect:/login?logout=true";
    }



    @Autowired
    private HttpServletRequest request;

    @GetMapping("/profile")
    public String getProfile(Model model, @ModelAttribute("schedules") ArrayList<Schedule> schedules){
        ScheduleDtoUserAndPhotoDto<?> scheduleUserAndPhotoDto = profileService.getProfile(schedules);

        model.addAttribute("profilePicture", scheduleUserAndPhotoDto.getBase64Image());
        model.addAttribute("user", scheduleUserAndPhotoDto.getProfile());
        model.addAttribute("form", scheduleUserAndPhotoDto.getScheduleDto());
        String currentURI = request.getRequestURI();
        model.addAttribute("request",currentURI);

        if(scheduleUserAndPhotoDto.getProfile() instanceof DoctorProfile){
            return "profile_doctor";
        } else if (scheduleUserAndPhotoDto.getProfile() instanceof HospitalProfile) {
            return "profile_hospital";
        }else if(scheduleUserAndPhotoDto.getProfile() instanceof PharmacyProfile){
            return "profile_pharmacy";
        }
        throw new IllegalStateException("Profilul nu poate fi determinat ");
    }

    @GetMapping("/profileHospital")
    public String getHospitalProfileFromUserPerspective(@RequestParam String hospital,@RequestParam String address, Model model){
        ScheduleUserAndPhotoDto<HospitalProfile> scheduleUserAndPhotoDto = profileService.getHospitalProfileFromUserPerspective(hospital, address);

        model.addAttribute("profilePicture", scheduleUserAndPhotoDto.getBase64Image());
        model.addAttribute("user", scheduleUserAndPhotoDto.getProfile());
        model.addAttribute("form", scheduleUserAndPhotoDto.getSchedules());

        return "profile_hospital";
    }

    @GetMapping("/profileDoctor")
    public String getDoctorProfileFromUserPerspective(@RequestParam String firstName,@RequestParam String lastName,@RequestParam String specialty, Model model){
        ScheduleUserAndPhotoDto<DoctorProfile> scheduleUserAndPhotoDto = profileService.getDoctorProfileFromUserPerspective(firstName,lastName,specialty);

        List<Appointment> appointments = appointmentRepository.findAllByDoctorProfileId(scheduleUserAndPhotoDto.getProfile().getId());
        int dum = 0, count=0;
        for(Appointment appointment : appointments){
           if(appointment.getReview() != null){
               dum += appointment.getReview().getGrade();
               count++;
           }
        }
        Review review = null;

        if(count != 0)
            review = reviewRepository.findById(dum/count);

        model.addAttribute("review", review);
        model.addAttribute("profilePicture", scheduleUserAndPhotoDto.getBase64Image());
        model.addAttribute("user", scheduleUserAndPhotoDto.getProfile());
        model.addAttribute("form", scheduleUserAndPhotoDto.getSchedules());

        return "profile_doctor";
    }

    @GetMapping("/profilePharmacy")
    public String getPharmacyProfileFromUserPerspective(@RequestParam String name,@RequestParam String address, Model model){
        ScheduleUserAndPhotoDto<PharmacyProfile> scheduleUserAndPhotoDto = profileService.getPharmacyProfileFromUserPerspective(name, address);

        model.addAttribute("profilePicture", scheduleUserAndPhotoDto.getBase64Image());
        model.addAttribute("user", scheduleUserAndPhotoDto.getProfile());
        model.addAttribute("form", scheduleUserAndPhotoDto.getSchedules());
        return "profile_pharmacy";
    }
}
