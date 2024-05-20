package com.licenta.service;

import com.licenta.EmbeddedKeys.MedicinePharmacyProfileId;
import com.licenta.dto.EntityPaginationDto;
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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService{

    @Autowired
    private PharmacyProfileRepository pharmacyProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private MedicinePharmacyProfileRepository medicinePharmacyProfileRepository;

    @Autowired UserServiceImpl userService;

    @Override
    public void addMedicineToPharmacy(int id, int quantity) {
        Medicines medicines = medicineRepository.findById(id);


        User userLoggedIn = userService.getAuthenticationUser();

        PharmacyProfile pharmacyProfile = pharmacyProfileRepository.findByUserId(userLoggedIn.getId());

        MedicinePharmacyProfileId idForPharmMed = new MedicinePharmacyProfileId();
        idForPharmMed.setMedicine(medicines);
        idForPharmMed.setPharmacyProfile(pharmacyProfile);


        MedicinePharmacyProfile medicinePharmacyProfile = medicinePharmacyProfileRepository.findByMedicinesAndPharmacyProfile(medicines, pharmacyProfile);

        if (medicinePharmacyProfile == null) {
            medicinePharmacyProfile = new MedicinePharmacyProfile();
            medicinePharmacyProfile.setId(idForPharmMed);
        }

        medicinePharmacyProfile.setQuantity(quantity);
        medicinePharmacyProfileRepository.save(medicinePharmacyProfile);
    }

    @Override
    public EntityPaginationDto<Medicines> getMedicinesPagination(int page, String search) {
        int pageSize = 15;

        Page<Medicines> medicines;

        if(StringUtils.hasText(search))
            medicines = medicineRepository.findByName(search, PageRequest.of(page, pageSize));
        else
            medicines = medicineRepository.findAll(PageRequest.of(page, pageSize));

        return new EntityPaginationDto<Medicines>(medicines, medicines.getNumber(), medicines.getTotalPages());
    }

    @Override
    public void deleteMedicine(int id) {
        Medicines medicines = medicineRepository.findById(id);
        medicineRepository.deleteById(id);
    }

    @Override
    public EntityPaginationDto<Medicines> seeMedicinesForOnePharmacy(String name, String address, int page, String search) {
        PharmacyProfile pharmacyProfile = pharmacyProfileRepository.findByNameAndAddress(name, address);
        List<MedicinePharmacyProfile> medicinePharmacyProfiles;

        if (StringUtils.hasText(search)) {
            medicinePharmacyProfiles = medicinePharmacyProfileRepository
                    .getMedicinePharmacyProfileByPharmacyProfileAndMedicineName(pharmacyProfile, search);
        } else {
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

        return new EntityPaginationDto<>(medicinesPage, medicinesPage.getNumber(),medicinesPage.getTotalPages());
    }
}
