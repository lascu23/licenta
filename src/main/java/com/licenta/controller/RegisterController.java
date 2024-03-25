package com.licenta.controller;

import com.licenta.dto.*;
import com.licenta.entity.User;
import com.licenta.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @GetMapping("/register") //pagina de register
    public String getRegisterForm(Model model){
        model.addAttribute("user", new RegistrationDto());
        return "register";
    }

    @PostMapping("/register/save") //se salveaza un nou cont
    public String register(@Valid @ModelAttribute("user")RegistrationDto user){
        User existingUserEmail = userService.findByEmail(user.getEmail());
        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()){
            return "redirect:/register?fail";
        }
        userService.saveUser(user);
        return "redirect:/login?register=true";
    }

    @GetMapping("/addHospitalAccount") //pagina de adaugare a unui spital
    public String addHospitalAccount(Model model){
        model.addAttribute("hospitalUser", new HospitalProfileDto());
        return "hospitalAccount";
    }

    @PostMapping("/addHospitalAccount/save") //se salveaza contul spitalului
    public String saveHospitalAccount(@Valid @ModelAttribute("hospitalUser")HospitalProfileDto hospitalProfileDto){
        userService.saveHospitalUserAndProfile(hospitalProfileDto);
        return "redirect:/home";
    }

    @GetMapping("/addPharmacyAccount")//pagina de adaugare a unei farmacii
    public String addPharmacyAccount(Model model){
        model.addAttribute("pharmacyUser", new PharmacyDto());
        return "pharmacyAccount";
    }

    @PostMapping("/addPharmacyAccount/save")//se salveaza contul farmaciei
    public String savePharmacyAccount(@Valid @ModelAttribute("pharmacyUser")PharmacyDto pharmacyDto){
        userService.savePharmacyUserAndProfile(pharmacyDto);
        return "redirect:/home";
    }

    @GetMapping("/addDoctorAccount")//pagina de adaugare a unui doctor
    public String addDoctorAccount(Model model){
        model.addAttribute("doctorUser", new DoctorDto());
        return "doctorAccount";
    }

    @PostMapping("/addDoctorAccount/save")//se salveaza contul doctorului
    public String saveDoctorAccount(@Valid @ModelAttribute("doctorUser") DoctorDto doctorDto){
        userService.saveDoctorUserAndProfile(doctorDto);
        return "redirect:/home";
    }
}
