package com.licenta.controller;

import com.licenta.entity.DoctorProfile;
import com.licenta.entity.HospitalProfile;
import com.licenta.repository.DoctorProfileRepository;
import com.licenta.repository.HospitalProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class HospitalController {
    @Autowired
    private DoctorProfileRepository doctorProfileRepository;

    @Autowired
    private HospitalProfileRepository hospitalProfileRepository;

    @GetMapping("/profileImage/{id}")
    public ResponseEntity<byte[]> getProfileImageById(@PathVariable int id) {
        byte[] imageData = hospitalProfileRepository.getProfileImageById(id);

        // Verifică dacă imaginea există în baza de date pentru ID-ul dat
        if (imageData != null && imageData.length > 0) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Sau MediaType.IMAGE_PNG, în funcție de tipul imaginilor tale

            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/manageDoctors")
    public String getAllDoctors(Model model){
        List<DoctorProfile> doctorProfileList = doctorProfileRepository.findAll();
        model.addAttribute("doctors", doctorProfileList);
        return "doctors";
    }

//    @GetMapping("/hospitals")
//    public String getHospitals(Model model){
//        List<HospitalProfile> hospitalProfiles = hospitalProfileRepository.findAll();
//        model.addAttribute("hospitals", hospitalProfiles);
//        return "hospitals";
//    }

    @GetMapping("/hospitals")
    public String getHospitalsBase64(Model model){
        List<HospitalProfile> hospitalProfiles = hospitalProfileRepository.findAll();
        List<String> imageList = new ArrayList<>();
        for(HospitalProfile hospitalProfile : hospitalProfiles){
            String base64Image = Base64.getEncoder().encodeToString(hospitalProfile.getProfileImage());
            imageList.add(base64Image);
        }

        model.addAttribute("base64Images", imageList);
        model.addAttribute("hospitals", hospitalProfiles);
        return "hospitals";
    }
}
