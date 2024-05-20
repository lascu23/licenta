package com.licenta.controller;


import com.licenta.service.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DoctorsController {

    @Autowired
    private DoctorServiceImpl doctorService;

    @GetMapping("/doctors")
    public String getDoctorsPagination(Model model, @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(required = false, name = "search")String search){

       model.addAttribute("entity", doctorService.getDoctorsPagination(page, search));

        return "doctors";
    }

    @PostMapping("/deleteDoctor")
    public String deleteDoctor(@RequestParam("doctorId") int id){
        doctorService.deleteDoctor(id);
        return "redirect:/doctors";
    }
}
