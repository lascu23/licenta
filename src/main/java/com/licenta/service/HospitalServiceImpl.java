package com.licenta.service;

import com.licenta.dto.EntityPaginationDto;
import com.licenta.entity.DoctorProfile;
import com.licenta.entity.HospitalProfile;
import com.licenta.repository.DoctorProfileRepository;
import com.licenta.repository.HospitalProfileRepository;
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
public class HospitalServiceImpl implements HospitalService{

    @Autowired
    private DoctorProfileRepository doctorProfileRepository;

    @Autowired
    private HospitalProfileRepository hospitalProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public EntityPaginationDto<HospitalProfile> getHospitalsPagination(int page, String search) {
        int pageSize = 9;

        Page<HospitalProfile> hospitalProfiles;

        if(StringUtils.hasText(search))
            hospitalProfiles = hospitalProfileRepository.findByNameOrCity(search, PageRequest.of(page, pageSize));
        else
            hospitalProfiles = hospitalProfileRepository.findAll(PageRequest.of(page, pageSize));

        List<String> imageList = new ArrayList<>();
        for(HospitalProfile hospitalProfile : hospitalProfiles.getContent()){
            String base64Image = Base64.getEncoder().encodeToString(hospitalProfile.getProfileImage());
            imageList.add(base64Image);
        }

        return new EntityPaginationDto<HospitalProfile>(imageList, hospitalProfiles, hospitalProfiles.getNumber(), hospitalProfiles.getTotalPages());
    }

    @Override
    public void deleteHospital(int id) {
        HospitalProfile hospitalProfile = hospitalProfileRepository.findById(id);
        hospitalProfileRepository.deleteById(id);
        userRepository.delete(userRepository.findById(hospitalProfile.getUser().getId()));
    }
}
