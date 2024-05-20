package com.licenta.controller;

import com.licenta.service.MedicineServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MedicineController {

   @Autowired
   private MedicineServiceImpl medicineService;


    @GetMapping("/getMedicines")
    public String seeAllMedicines(Model model, @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(required = false, name = "search")String search){

        model.addAttribute("entity", medicineService.getMedicinesPagination(page, search));

        return "medicinesForPharmacies";
    }

    @PostMapping("/deleteMedicine")
    public String deleteDoctor(@RequestParam("medicineId") int id){

        medicineService.deleteMedicine(id);
        return "redirect:/getMedicines";
    }

    @PostMapping("/addMedicineToPharmacy")
    public String addMedicineToPharmacy(@RequestParam("medicineId") int id,@RequestParam("quantity") int quantity){
        medicineService.addMedicineToPharmacy(id, quantity);
        return "redirect:/getMedicines";
    }

    @GetMapping("/seeMedicinesForOnePharmacy")
    public String seeMedicinesForOnePharmacy(@RequestParam String name, @RequestParam String address, Model model,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(required = false, name = "search") String search) {
        model.addAttribute("entity", medicineService.seeMedicinesForOnePharmacy(name, address, page, search));
        return "medicinesForOnePharmacy";
    }
}
