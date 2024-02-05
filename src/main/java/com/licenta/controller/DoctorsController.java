package com.licenta.controller;


import com.licenta.entity.DoctorProfile;
import com.licenta.entity.HospitalProfile;
import com.licenta.repository.DoctorProfileRepository;
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
    private DoctorProfileRepository doctorProfileRepository;


    @GetMapping("/manageDoctors")
    public String getAllDoctorsForHospital(Model model){
        List<DoctorProfile> doctorProfileList = doctorProfileRepository.findAll();
        model.addAttribute("doctors", doctorProfileList);
        return "doctors";
    }

//    @GetMapping("/doctors")
//    public String getAllDoctors(Model model){
//        List<DoctorProfile> doctorProfileList = doctorProfileRepository.findAll();
//        model.addAttribute("doctors", doctorProfileList);
//        return "doctors";
//    }

    @GetMapping("/doctors")
    public String getDoctorsPagination(Model model, @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(required = false, name = "search")String search){
        int pageSize = 3;

        Page<DoctorProfile> doctorProfiles;

        if(StringUtils.hasText(search))
            doctorProfiles = doctorProfileRepository.findByFirstNameOrLastName(search, PageRequest.of(page, pageSize));
        else
            doctorProfiles = doctorProfileRepository.findAll(PageRequest.of(page, pageSize));

        List<String> imageList = new ArrayList<>();
        for(DoctorProfile doctorProfile : doctorProfiles.getContent()){
            String base64Image = Base64.getEncoder().encodeToString(doctorProfile.getProfileImage());
            imageList.add(base64Image);
        }

        model.addAttribute("base64Images", imageList);
        model.addAttribute("doctorProfiles", doctorProfiles);
        model.addAttribute("currentPage", doctorProfiles.getNumber());
        model.addAttribute("totalPages", doctorProfiles.getTotalPages());
        return "doctorsPagination";
    }

    @PostMapping("/deleteDoctor")
    public String deleteDoctor(@RequestParam("doctorId") int id){
        doctorProfileRepository.deleteById(id);
        return "redirect:/doctors";
    }
}
