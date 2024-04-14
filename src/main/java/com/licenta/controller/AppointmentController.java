package com.licenta.controller;

import com.licenta.entity.*;
import com.licenta.repository.*;
import com.licenta.service.AppointmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class AppointmentController {

    private final AppointmentServiceImpl appointmentService;

    public AppointmentController(AppointmentServiceImpl appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/makeAppointment") //poti crea o programare la doctor
    public String makeAppointment(Model model, HttpServletRequest request){//, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String specialty){
        model.addAttribute("appointment", appointmentService.makeAppointment(request));
        return "appointment";
    }

    @GetMapping("/makeAppointment/save") //salveaza programarea
    public String saveAppointment(@ModelAttribute("appointment") Appointment appointment){
        appointmentService.saveAppointment(appointment);
        return "redirect:/home";
    }

    @GetMapping("/getAvailableHours")
    @ResponseBody
    public List<LocalTime> getAvailableHours(@RequestParam int doctorId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return appointmentService.getAvailableHours(doctorId, date);
    }


}
