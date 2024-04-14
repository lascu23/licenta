package com.licenta.controller;

import com.licenta.dto.ScheduleDto;
import com.licenta.entity.*;
import com.licenta.repository.MedicinePharmacyProfileRepository;
import com.licenta.repository.MedicineRepository;
import com.licenta.repository.PharmacyProfileRepository;
import com.licenta.repository.UserRepository;
import com.licenta.service.PharmacyServiceImpl;
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
    private PharmacyServiceImpl pharmacyService;

    @GetMapping("/pharmacies") //afiseaza toate farmaciile existente
    public String getHospitalsPagination(Model model, @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(required = false, name = "search")String search){
        model.addAttribute("entity", pharmacyService.getPharmacyPagination(page, search));
        return "pharmacies";
    }

    @PostMapping("/deletePharmacy") //sterge o farmacie
    public String deleteHospital(@RequestParam("pharmacyId") int id){
        pharmacyService.deletePharmacy(id);
        return "redirect:/pharmacies";
    }


}
