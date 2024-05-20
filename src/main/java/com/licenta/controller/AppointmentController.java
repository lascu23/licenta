package com.licenta.controller;

import com.licenta.entity.*;
import com.licenta.service.AppointmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class AppointmentController {

    private final AppointmentServiceImpl appointmentService;

    public AppointmentController(AppointmentServiceImpl appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/makeAppointment")
    public String makeAppointment(Model model, HttpServletRequest request){
        model.addAttribute("appointment", appointmentService.makeAppointment(request));
        return "appointment";
    }

    @GetMapping("/makeAppointment/save")
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
