package com.licenta.service;

import com.licenta.dto.EntityPaginationDto;
import com.licenta.entity.DoctorProfile;
import com.licenta.repository.DoctorProfileRepository;
import com.licenta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorProfileRepository doctorProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void deleteDoctor(int id) {
        DoctorProfile doctorProfile = doctorProfileRepository.findById(id);
        doctorProfileRepository.deleteById(id);
        userRepository.delete(userRepository.findById(doctorProfile.getUser().getId()));
    }

    @Override
    public EntityPaginationDto<DoctorProfile> getDoctorsPagination(int page, String search) {
        int pageSize = 9;

        Page<DoctorProfile> doctorProfiles;

        if(StringUtils.hasText(search))
            doctorProfiles = doctorProfileRepository.findByFirstNameOrLastName(search, PageRequest.of(page, pageSize));
        else
            doctorProfiles = doctorProfileRepository.findAll(PageRequest.of(page, pageSize));

        List<String> imageList = new ArrayList<>();
        for(DoctorProfile doctorProfile : doctorProfiles.getContent()){
            String base64Image = Base64.getEncoder().encodeToString(doctorProfile.getProfileImage());
            imageList.add(base64Image);
        }

        return new EntityPaginationDto<DoctorProfile>(imageList, doctorProfiles, doctorProfiles.getNumber(), doctorProfiles.getTotalPages());
    }
}
