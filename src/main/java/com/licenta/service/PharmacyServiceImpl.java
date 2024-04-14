package com.licenta.service;

import com.licenta.dto.EntityPaginationDto;
import com.licenta.entity.DoctorProfile;
import com.licenta.entity.HospitalProfile;
import com.licenta.entity.PharmacyProfile;
import com.licenta.repository.PharmacyProfileRepository;
import com.licenta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class PharmacyServiceImpl implements PharmacyService{

    private final PharmacyProfileRepository pharmacyProfileRepository;

    private final UserRepository userRepository;

    public PharmacyServiceImpl(PharmacyProfileRepository pharmacyProfileRepository, UserRepository userRepository) {
        this.pharmacyProfileRepository = pharmacyProfileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public EntityPaginationDto<PharmacyProfile> getPharmacyPagination(int page, String search) {
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

        return new EntityPaginationDto<PharmacyProfile>(imageList, pharmacyProfiles, pharmacyProfiles.getNumber(), pharmacyProfiles.getTotalPages());
    }

    @Override
    public void deletePharmacy(int id) {
        PharmacyProfile pharmacyProfile = pharmacyProfileRepository.findById(id);
        pharmacyProfileRepository.deleteById(id);
        userRepository.delete(userRepository.findById(pharmacyProfile.getUser().getId()));
    }
}
