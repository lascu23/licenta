package com.licenta.controller;

import com.licenta.dto.AppointmentDto;
import com.licenta.dto.CalendarDto;
import com.licenta.dto.ShowCalendarDto;
import com.licenta.entity.Appointment;
import com.licenta.entity.DoctorProfile;
import com.licenta.entity.User;
import com.licenta.repository.AppointmentRepository;
import com.licenta.repository.DoctorProfileRepository;
import com.licenta.repository.UserRepository;
import com.licenta.service.CalendarServiceImpl;
import com.licenta.service.UserService;
import com.licenta.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
public class CalendarController {

    @Autowired
    private CalendarServiceImpl calendarService;

    @GetMapping("/calendar")
    public String showCalendar(@RequestParam(required = false, defaultValue = "0") int year,
                               @RequestParam(required = false, defaultValue = "0") int month,
                               Model model) {

        ShowCalendarDto calendarDto = calendarService.createCalendar(year, month);

        model.addAttribute("calendar", calendarDto.getCalendar());
        model.addAttribute("currentMonthName", calendarDto.getCurrentMonthName());
        model.addAttribute("daysOfWeek", calendarDto.getDaysOfWeek());
        model.addAttribute("currentMonth", calendarDto.getCurrentMonth());
        model.addAttribute("currentYear", calendarDto.getCurrentYear());

        return "calendar";
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
}
