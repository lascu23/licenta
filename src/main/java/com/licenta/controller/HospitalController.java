package com.licenta.controller;

import com.licenta.entity.DoctorProfile;
import com.licenta.entity.HospitalProfile;
import com.licenta.entity.User;
import com.licenta.repository.DoctorProfileRepository;
import com.licenta.repository.HospitalProfileRepository;
import com.licenta.repository.UserRepository;
import com.licenta.service.HospitalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class HospitalController {

    @Autowired
    private HospitalServiceImpl hospitalService;

    @GetMapping("/hospitals") //afiseaza toate spitalele existente
    public String getHospitalsPagination(Model model, @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(required = false, name = "search")String search){

        model.addAttribute("entity", hospitalService.getHospitalsPagination(page, search));

        return "hospitals";
    }

    @PostMapping("/deleteHospital") //sterge un spital
    public String deleteHospital(@RequestParam("hospitalId") int id){
        hospitalService.deleteHospital(id);
        return "redirect:/hospitals";
    }



}
