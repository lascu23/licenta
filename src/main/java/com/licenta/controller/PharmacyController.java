package com.licenta.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class PharmacyController {
    @Autowired
    private PharmacyProfileRepository pharmacyProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private MedicinePharmacyProfileRepository medicinePharmacyProfileRepository;

    @GetMapping("/pharmacies")
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

    @PostMapping("/deletePharmacy")
    public String deleteHospital(@RequestParam("pharmacyId") int id){
        PharmacyProfile pharmacyProfile = pharmacyProfileRepository.findById(id);
        pharmacyProfileRepository.deleteById(id);
        userRepository.delete(userRepository.findById(pharmacyProfile.getUser().getId()));

        return "redirect:/pharmacies";
    }

    @GetMapping("/getMedicines")
    public String seeAllMedicines(Model model, @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(required = false, name = "search")String search){
        int pageSize = 15;

        Page<Medicines> medicines;

        if(StringUtils.hasText(search))
            medicines = medicineRepository.findByName(search, PageRequest.of(page, pageSize));
        else
            medicines = medicineRepository.findAll(PageRequest.of(page, pageSize));


        model.addAttribute("medicines", medicines);
        model.addAttribute("currentPage", medicines.getNumber());
        model.addAttribute("totalPages", medicines.getTotalPages());
        return "medicinesForPharmacies";
    }

    @PostMapping("/deleteMedicine")
    public String deleteDoctor(@RequestParam("medicineId") int id){

        Medicines medicines = medicineRepository.findById(id);
        medicineRepository.deleteById(id);
        return "redirect:/getMedicines";
    }

    @PostMapping("/addMedicineToPharmacy")
    public String addMedicineTopHarmacy(@RequestParam("medicineId") int id,@RequestParam("quantity") int quantity){
        Medicines medicines = medicineRepository.findById(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userLoggedIn = userRepository.findByEmail(authentication.getName());

        PharmacyProfile pharmacyProfile = pharmacyProfileRepository.findByUserId(userLoggedIn.getId());

        MedicinePharmacyProfileId idForPharmMed = new MedicinePharmacyProfileId();
        idForPharmMed.setMedicine(medicines);
        idForPharmMed.setPharmacyProfile(pharmacyProfile);


        MedicinePharmacyProfile medicinePharmacyProfile = medicinePharmacyProfileRepository.findByMedicinesAndPharmacyProfile(medicines, pharmacyProfile);

        if (medicinePharmacyProfile == null) {
            // Dacă nu există, creează o nouă înregistrare în tabela asociativă
            medicinePharmacyProfile = new MedicinePharmacyProfile();
            medicinePharmacyProfile.setId(idForPharmMed);
        }

        medicinePharmacyProfile.setQuantity(quantity);
        medicinePharmacyProfileRepository.save(medicinePharmacyProfile);

        return "redirect:/getMedicines";
    }

//    @GetMapping("/seeMedicinesForOnePharmacy")
//    public String seeMedicinesForOnePharmacy(@RequestParam String name, @RequestParam String address, Model model,
//                                             @RequestParam(defaultValue = "0") int page) {
//        PharmacyProfile pharmacyProfile = pharmacyProfileRepository.findByNameAndAddress(name, address);
//        List<MedicinePharmacyProfile> medicinePharmacyProfiles = medicinePharmacyProfileRepository
//                .getMedicinePharmacyProfileByPharmacyProfile(pharmacyProfile);
//        List<Medicines> medicinesList = new ArrayList<>();
//
//        for (MedicinePharmacyProfile medicinePharmacyProfile : medicinePharmacyProfiles) {
//            Medicines medicines = medicineRepository.findById(medicinePharmacyProfile.getMedicines().getId());
//            medicinesList.add(medicines);
//        }
//
//        int pageSize = 15;
//        int start = page * pageSize;
//        int end = Math.min((page + 1) * pageSize, medicinesList.size());
//
//        Page<Medicines> medicinesPage = new PageImpl<>(medicinesList.subList(start, end), PageRequest.of(page, pageSize),
//                medicinesList.size());
//
//        model.addAttribute("medicines", medicinesPage);
//        model.addAttribute("currentPage", medicinesPage.getNumber());
//        model.addAttribute("totalPages", medicinesPage.getTotalPages());
//        return "medicinesForOnePharmacy";
//    }
@GetMapping("/seeMedicinesForOnePharmacy")
public String seeMedicinesForOnePharmacy(@RequestParam String name, @RequestParam String address, Model model,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(required = false, name = "search") String search) {
    PharmacyProfile pharmacyProfile = pharmacyProfileRepository.findByNameAndAddress(name, address);
    List<MedicinePharmacyProfile> medicinePharmacyProfiles;

    if (StringUtils.hasText(search)) {
        // Caută după nume dacă parametrul de căutare este furnizat
        medicinePharmacyProfiles = medicinePharmacyProfileRepository
                .getMedicinePharmacyProfileByPharmacyProfileAndMedicineName(pharmacyProfile, search);
    } else {
        // Altfel, obține toate medicamentele asociate farmaciei
        medicinePharmacyProfiles = medicinePharmacyProfileRepository
                .getMedicinePharmacyProfileByPharmacyProfile(pharmacyProfile);
    }

    List<Medicines> medicinesList = new ArrayList<>();

    for (MedicinePharmacyProfile medicinePharmacyProfile : medicinePharmacyProfiles) {
        Medicines medicines = medicinePharmacyProfile.getMedicines();
        medicinesList.add(medicines);
    }

    int pageSize = 15;
    int start = page * pageSize;
    int end = Math.min((page + 1) * pageSize, medicinesList.size());

    Page<Medicines> medicinesPage = new PageImpl<>(medicinesList.subList(start, end),
            PageRequest.of(page, pageSize), medicinesList.size());

    model.addAttribute("medicines", medicinesPage);
    model.addAttribute("currentPage", medicinesPage.getNumber());
    model.addAttribute("totalPages", medicinesPage.getTotalPages());
    return "medicinesForOnePharmacy";
}


}
