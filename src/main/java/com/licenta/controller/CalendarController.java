package com.licenta.controller;

import com.licenta.dto.ShowCalendarDto;
import com.licenta.entity.Appointment;
import com.licenta.entity.PatientProfile;
import com.licenta.entity.Prescription;
import com.licenta.entity.PrescriptionMedicine;
import com.licenta.service.CalendarServiceImpl;
import com.licenta.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
public class CalendarController {

    @Autowired
    private CalendarServiceImpl calendarService;



    @GetMapping("/calendarDoctor")
    public String showDoctorCalendar(@RequestParam(required = false, defaultValue = "0") int year,
                               @RequestParam(required = false, defaultValue = "0") int month,
                               Model model) {

        ShowCalendarDto calendarDto = calendarService.createDoctorCalendar(year, month);

        model.addAttribute("calendar", calendarDto.getCalendar());
        model.addAttribute("currentMonthName", calendarDto.getCurrentMonthName());
        model.addAttribute("daysOfWeek", calendarDto.getDaysOfWeek());
        model.addAttribute("currentMonth", calendarDto.getCurrentMonth());
        model.addAttribute("currentYear", calendarDto.getCurrentYear());

        return "calendarDoctor";
    }

    @GetMapping("/getAppointments")
    @ResponseBody
    public List<Appointment> getAppointments(@RequestParam int day, @RequestParam int currentMonth, @RequestParam int currentYear){
        return calendarService.getAppointments(day, currentMonth, currentYear);
    }

    @PutMapping("/markAppointmentFulfilled/{id}")
    public ResponseEntity<?> markAppointmentAsFulfilled(@PathVariable("id") int id) {
        return calendarService.markAppointmentAsFulfilled(id);
    }



    @GetMapping("/calendarPatient")
    public String getPatientCalendar(Model model){
        model.addAttribute("appointments", calendarService.getPatientAppointmentsForCalendar());
        model.addAttribute("prescriptions", calendarService.getPrescriptionsForPatient());
        return "calendarPatient";
    }


}
