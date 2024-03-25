package com.licenta.controller;

import com.licenta.entity.DoctorProfile;
import com.licenta.entity.HospitalProfile;
import com.licenta.entity.PatientProfile;
import com.licenta.entity.User;
import com.licenta.repository.DoctorProfileRepository;
import com.licenta.repository.HospitalProfileRepository;
import com.licenta.repository.PatientProfileRepository;
import com.licenta.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppointmentController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientProfileRepository patientProfileRepository;
    @Autowired
    private DoctorProfileRepository doctorProfileRepository;


    //TREBUIE MAI INTAI SA FAC PROGRAM DE LUCRU AL DOCTORULUI CA SA POT FACE UN APPOINTMENT
    //ALTFEL NU STIU LA CE ORA SA L FAC


    @GetMapping("/makeAppointment")
    public String makeAppointment(Model model, HttpServletRequest request){//, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String specialty){
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String specialty = request.getParameter("specialty");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userLoggedIn = userRepository.findByEmail(authentication.getName());

        PatientProfile patientProfile = patientProfileRepository.findByUserId(userLoggedIn.getId());
        DoctorProfile doctorProfile = doctorProfileRepository.findByFirstNameAndLastNameAndSpecialty(firstName, lastName, specialty);

        model.addAttribute("doctorName", doctorProfile.getLastName() +" "+ doctorProfile.getFirstName());
        model.addAttribute("patientName", patientProfile.getLastName() +" "+ patientProfile.getFirstName());
        return "appointment";
    }


}
