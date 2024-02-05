package com.licenta.controller;

import com.licenta.entity.DoctorProfile;
import com.licenta.entity.HospitalProfile;
import com.licenta.entity.User;
import com.licenta.repository.DoctorProfileRepository;
import com.licenta.repository.HospitalProfileRepository;
import com.licenta.repository.UserRepository;
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
    private DoctorProfileRepository doctorProfileRepository;

    @Autowired
    private HospitalProfileRepository hospitalProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/hospitals")
    public String getHospitalsPagination(Model model, @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(required = false, name = "search")String search){
        int pageSize = 3;

        Page<HospitalProfile> hospitalProfiles;

        if(StringUtils.hasText(search))
            hospitalProfiles = hospitalProfileRepository.findByNameOrCity(search,PageRequest.of(page, pageSize));
        else
            hospitalProfiles = hospitalProfileRepository.findAll(PageRequest.of(page, pageSize));

        List<String> imageList = new ArrayList<>();
        for(HospitalProfile hospitalProfile : hospitalProfiles.getContent()){
            String base64Image = Base64.getEncoder().encodeToString(hospitalProfile.getProfileImage());
            imageList.add(base64Image);
        }

        model.addAttribute("base64Images", imageList);
        model.addAttribute("hospitalProfiles", hospitalProfiles);
        model.addAttribute("currentPage", hospitalProfiles.getNumber());
        model.addAttribute("totalPages", hospitalProfiles.getTotalPages());
        return "hospitalsPagination";
    }

    @PostMapping("/deleteHospital")
    public String deleteHospital(@RequestParam("hospitalId") int id){
        hospitalProfileRepository.deleteById(id);
        return "redirect:/hospitals";
    }



}
