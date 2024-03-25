package com.licenta.controller;

import com.licenta.dto.ScheduleDto;
import com.licenta.entity.*;
import com.licenta.repository.MedicinePharmacyProfileRepository;
import com.licenta.repository.MedicineRepository;
import com.licenta.repository.PharmacyProfileRepository;
import com.licenta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@Controller
public class PharmacyController {
    @Autowired
    private PharmacyProfileRepository pharmacyProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/pharmacies") //afiseaza toate farmaciile existente
    public String getHospitalsPagination(Model model, @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(required = false, name = "search")String search){
        int pageSize = 15;

        Page<PharmacyProfile> pharmacyProfiles;

        if(StringUtils.hasText(search))
            pharmacyProfiles = pharmacyProfileRepository.findByNameOrCity(search, PageRequest.of(page, pageSize));
        else
            pharmacyProfiles = pharmacyProfileRepository.findAll(PageRequest.of(page, pageSize));

        List<String> imageList = new ArrayList<>();
        for(PharmacyProfile pharmacyProfile : pharmacyProfiles.getContent()){
            String base64Image = Base64.getEncoder().encodeToString(pharmacyProfile.getProfileImage());
            imageList.add(base64Image);
        }

        model.addAttribute("base64Images", imageList);
        model.addAttribute("pharmacyProfiles", pharmacyProfiles);
        model.addAttribute("currentPage", pharmacyProfiles.getNumber());
        model.addAttribute("totalPages", pharmacyProfiles.getTotalPages());
        return "pharmacyPagination";
    }

    @PostMapping("/deletePharmacy") //sterge o farmacie
    public String deleteHospital(@RequestParam("pharmacyId") int id){
        PharmacyProfile pharmacyProfile = pharmacyProfileRepository.findById(id);
        pharmacyProfileRepository.deleteById(id);
        userRepository.delete(userRepository.findById(pharmacyProfile.getUser().getId()));

        return "redirect:/pharmacies";
    }


}
