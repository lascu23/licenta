package com.licenta.controller;

import com.licenta.dto.ScheduleDto;
import com.licenta.service.ScheduleServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


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
