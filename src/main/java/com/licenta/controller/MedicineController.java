package com.licenta.controller;

import com.licenta.EmbeddedKeys.MedicinePharmacyProfileId;
import com.licenta.entity.*;
import com.licenta.repository.MedicinePharmacyProfileRepository;
import com.licenta.repository.MedicineRepository;
import com.licenta.repository.PharmacyProfileRepository;
import com.licenta.repository.UserRepository;
import com.licenta.service.MedicineServiceImpl;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MedicineController {

   @Autowired
   private MedicineServiceImpl medicineService;


    @GetMapping("/getMedicines") //afiseaza toate medicamentele
    public String seeAllMedicines(Model model, @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(required = false, name = "search")String search){

        model.addAttribute("entity", medicineService.getMedicinesPagination(page, search));

        return "medicinesForPharmacies";
    }

    @PostMapping("/deleteMedicine") //sterge un medicament
    public String deleteDoctor(@RequestParam("medicineId") int id){

        medicineService.deleteMedicine(id);
        return "redirect:/getMedicines";
    }

    @PostMapping("/addMedicineToPharmacy")//adauga un medicament in inventarul unei farmacii
    public String addMedicineToPharmacy(@RequestParam("medicineId") int id,@RequestParam("quantity") int quantity){
        medicineService.addMedicineToPharmacy(id, quantity);
        return "redirect:/getMedicines";
    }

    @GetMapping("/seeMedicinesForOnePharmacy") //afiseaza toate medicamentele unei anumite farmacii
    public String seeMedicinesForOnePharmacy(@RequestParam String name, @RequestParam String address, Model model,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(required = false, name = "search") String search) {
        model.addAttribute("entity", medicineService.seeMedicinesForOnePharmacy(name, address, page, search));
        return "medicinesForOnePharmacy";
    }
}
