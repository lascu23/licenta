package com.licenta.controller;

import com.licenta.dto.RegisterPatientDto;
import com.licenta.dto.ScheduleDto;
import com.licenta.dto.ScheduleDtoUserAndPhotoDto;
import com.licenta.dto.ScheduleUserAndPhotoDto;
import com.licenta.entity.*;
import com.licenta.entity.SchedulePharmacyProfile;
import com.licenta.repository.*;
import com.licenta.service.ProfileServiceImpl;
import com.licenta.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@Controller
public class ProfilController {
    final
    ProfileServiceImpl profileService;

    private final UserServiceImpl userService;

    public ProfilController(ProfileServiceImpl profileService, UserServiceImpl userService) {
        this.profileService = profileService;
        this.userService = userService;
    }


    @GetMapping("/profilePatient") //afiseaza profilul pacientului logat
    public String getProfilePatient(Model model){

        model.addAttribute("user", profileService.showLoggedInPatientProfile());
        model.addAttribute("userEmail", userService.getAuthenticationUser().getEmail());
        return "createProfilePatient";
    }

    @PostMapping("/profilePatient/save")//salveaza orice modificare adusa profilului
    public String saveProfilePatient(@ModelAttribute("user") PatientProfile patientProfile){
        profileService.savePatientProfile(patientProfile);
        return "redirect:/home";
    }

    @GetMapping("/deleteAccount") //sterge contul unui pacient
    public String deleteAccount(@RequestParam("userEmail") String email, HttpServletRequest request, HttpServletResponse response){
        profileService.deleteAccount(email, request, response);
        return "redirect:/login?logout=true";
    }



    @GetMapping("/profile") //afiseaza profilul unei entitati in functie de logare
    public String getProfile(Model model, @ModelAttribute("schedules") ArrayList<Schedule> schedules){
        //profilePicture user schedules
        ScheduleDtoUserAndPhotoDto<?> scheduleUserAndPhotoDto = profileService.getProfile(schedules);

        model.addAttribute("profilePicture", scheduleUserAndPhotoDto.getBase64Image());
        model.addAttribute("user", scheduleUserAndPhotoDto.getProfile());
        model.addAttribute("form", scheduleUserAndPhotoDto.getScheduleDto());

        if(scheduleUserAndPhotoDto.getProfile() instanceof DoctorProfile){
            return "profile_doctor";
        } else if (scheduleUserAndPhotoDto.getProfile() instanceof HospitalProfile) {
            return "profile_hospital";
        }else if(scheduleUserAndPhotoDto.getProfile() instanceof PharmacyProfile){
            return "profile_pharmacy";
        }
        throw new IllegalStateException("Profilul nu poate fi determinat ");
    }

    @GetMapping("/profileHospital")// din lista tuturor spitalelor, daca apas pe unu ma duce sa i vad pagina de profil
    public String getHospitalProfileFromUserPerspective(@RequestParam String hospital,@RequestParam String address, Model model){
        ScheduleUserAndPhotoDto<HospitalProfile> scheduleUserAndPhotoDto = profileService.getHospitalProfileFromUserPerspective(hospital, address);

        model.addAttribute("profilePicture", scheduleUserAndPhotoDto.getBase64Image());
        model.addAttribute("user", scheduleUserAndPhotoDto.getProfile());
        model.addAttribute("form", scheduleUserAndPhotoDto.getSchedules());

        return "profile_hospital";
    }

    @GetMapping("/profileDoctor")// din lista tuturor doctorilor, daca apas pe unu ma duce sa i vad pagina de profil
    public String getDoctorProfileFromUserPerspective(@RequestParam String firstName,@RequestParam String lastName,@RequestParam String specialty, Model model){
        ScheduleUserAndPhotoDto<DoctorProfile> scheduleUserAndPhotoDto = profileService.getDoctorProfileFromUserPerspective(firstName,lastName,specialty);

        model.addAttribute("profilePicture", scheduleUserAndPhotoDto.getBase64Image());
        model.addAttribute("user", scheduleUserAndPhotoDto.getProfile());
        model.addAttribute("form", scheduleUserAndPhotoDto.getSchedules());

        return "profile_doctor";
    }

    @GetMapping("/profilePharmacy")// din lista tuturor farmaciilor, daca apas pe una ma duce sa i vad pagina de profil
    public String getPharmacyProfileFromUserPerspective(@RequestParam String name,@RequestParam String address, Model model){
        ScheduleUserAndPhotoDto<PharmacyProfile> scheduleUserAndPhotoDto = profileService.getPharmacyProfileFromUserPerspective(name, address);

        model.addAttribute("profilePicture", scheduleUserAndPhotoDto.getBase64Image());
        model.addAttribute("user", scheduleUserAndPhotoDto.getProfile());
        model.addAttribute("form", scheduleUserAndPhotoDto.getSchedules());
        return "profile_pharmacy";
    }
}
