package com.licenta.controller;


import com.licenta.entity.DoctorProfile;
import com.licenta.entity.HospitalProfile;
import com.licenta.repository.DoctorProfileRepository;
import com.licenta.repository.UserRepository;
import com.licenta.service.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class DoctorsController {

    @Autowired
    private DoctorServiceImpl doctorService;

    @GetMapping("/doctors") //afiseaza toti doctorii existenti
    public String getDoctorsPagination(Model model, @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(required = false, name = "search")String search){

       model.addAttribute("entity", doctorService.getDoctorsPagination(page, search));

        return "doctors";
    }

    @PostMapping("/deleteDoctor") //sterge un doctor
    public String deleteDoctor(@RequestParam("doctorId") int id){
        doctorService.deleteDoctor(id);
        return "redirect:/doctors";
    }
}
