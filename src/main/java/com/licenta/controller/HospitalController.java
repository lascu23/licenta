package com.licenta.controller;

import com.licenta.service.HospitalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HospitalController {

    @Autowired
    private HospitalServiceImpl hospitalService;

    @GetMapping("/hospitals")
    public String getHospitalsPagination(Model model, @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(required = false, name = "search")String search){

        model.addAttribute("entity", hospitalService.getHospitalsPagination(page, search));

        return "hospitals";
    }

    @PostMapping("/deleteHospital")
    public String deleteHospital(@RequestParam("hospitalId") int id){
        hospitalService.deleteHospital(id);
        return "redirect:/hospitals";
    }



}
