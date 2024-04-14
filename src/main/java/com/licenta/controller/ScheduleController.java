package com.licenta.controller;

import com.licenta.EmbeddedKeys.ScheduleDoctorProfileId;
import com.licenta.EmbeddedKeys.ScheduleHospitalProfileId;
import com.licenta.EmbeddedKeys.SchedulePharmacyProfileId;
import com.licenta.dto.ScheduleDto;
import com.licenta.entity.*;
import com.licenta.repository.*;
import com.licenta.service.ScheduleServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ScheduleController {


    final ScheduleServiceImpl scheduleService;

    public ScheduleController(ScheduleServiceImpl scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedule/save")
    public String saveSchedules(@ModelAttribute ScheduleDto form, Model model){
        return "redirect:/profile" + scheduleService.saveSchedules(form);
    }


    @GetMapping("/deleteSchedule")
    public String deleteSchedule(){
        scheduleService.deleteSchedule();
        return "redirect:/profile";
    }
}
