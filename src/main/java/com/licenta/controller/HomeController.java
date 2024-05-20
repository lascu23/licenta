package com.licenta.controller;

import com.licenta.entity.DoctorProfile;
import com.licenta.entity.HospitalProfile;
import com.licenta.repository.DoctorProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private DoctorProfileRepository doctorProfileRepository;

    @GetMapping(value = {"/","","/home"})
    public String getHomePage(Model model){

        List<Object[]> doctorRatingList = doctorProfileRepository.findDoctorByBestAverageRating();

        List<String> firstNames = new ArrayList<>();
        List<String> lastNames = new ArrayList<>();
        List<String> profilePics = new ArrayList<>();
        List<String> hospitalProfilesNames = new ArrayList<>();
        List<String> hospitalProfilesAddresses = new ArrayList<>();
        List<Integer> reviews = new ArrayList<>();

        doctorRatingList.stream().limit(3).forEach(result -> {
            DoctorProfile doctorProfile = (DoctorProfile) result[0];
            Integer review = ((Double) result[1]).intValue();

            firstNames.add(doctorProfile.getFirstName());
            lastNames.add(doctorProfile.getLastName());
            profilePics.add(Base64.getEncoder().encodeToString(doctorProfile.getProfileImage()));
            hospitalProfilesNames.add(doctorProfile.getHospitalProfile().getName());
            hospitalProfilesAddresses.add(doctorProfile.getHospitalProfile().getAddress());
            reviews.add(review);
        });

        model.addAttribute("firstNames", firstNames);
        model.addAttribute("lastNames", lastNames);
        model.addAttribute("profilePics", profilePics);
        model.addAttribute("hospitalProfilesNames", hospitalProfilesNames);
        model.addAttribute("hospitalProfilesAddresses", hospitalProfilesAddresses);
        model.addAttribute("reviews", reviews);

        return "index";
    }
}
