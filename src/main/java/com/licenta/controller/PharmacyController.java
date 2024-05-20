package com.licenta.controller;

import com.licenta.service.PharmacyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PharmacyController {

    @Autowired
    private PharmacyServiceImpl pharmacyService;

    @GetMapping("/pharmacies")
    public String getHospitalsPagination(Model model, @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(required = false, name = "search")String search,
                                         @RequestParam(required = false, name = "searchMedicine") String medicine){
        model.addAttribute("entity", pharmacyService.getPharmacyPagination(page, search, medicine));
        return "pharmacies";
    }

    @PostMapping("/deletePharmacy")
    public String deleteHospital(@RequestParam("pharmacyId") int id){
        pharmacyService.deletePharmacy(id);
        return "redirect:/pharmacies";
    }


}
