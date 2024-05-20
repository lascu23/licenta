package com.licenta.service;

import com.licenta.dto.DoctorDto;
import com.licenta.dto.HospitalProfileDto;
import com.licenta.dto.PharmacyDto;
import com.licenta.dto.RegistrationDto;
import com.licenta.entity.*;
import com.licenta.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HospitalProfileRepository hospitalProfileRepository;

    @Autowired
    private DoctorProfileRepository doctorProfileRepository;

    @Autowired
    private PharmacyProfileRepository pharmacyProfileRepository;


    @Override
    public User getAuthenticationUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName());
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = roleRepository.findByName("PATIENT");
        user.setRole(role);
        userRepository.save(user);
    }


    @Override
    public void saveHospitalUserAndProfile(HospitalProfileDto hospitalProfileDto){
        User user = new User();
        user.setEmail(hospitalProfileDto.getEmail());
        user.setPassword(passwordEncoder.encode(hospitalProfileDto.getPassword()));
        Role role = roleRepository.findByName("HOSPITAL");
        user.setRole(role);
        userRepository.save(user);


        HospitalProfile hospitalProfile = new HospitalProfile();
        hospitalProfile.setName(hospitalProfileDto.getName());
        hospitalProfile.setCity(hospitalProfileDto.getCity());
        hospitalProfile.setCountry(hospitalProfileDto.getCountry());
        hospitalProfile.setAddress(hospitalProfileDto.getAddress());
        hospitalProfile.setUser(user);

        MultipartFile file = hospitalProfileDto.getProfileImage();
        try{
            if(file!=null && !file.isEmpty()){
                hospitalProfile.setProfileImage(file.getBytes());
            }
            hospitalProfileRepository.save(hospitalProfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
}

    @Override
    public void savePharmacyUserAndProfile(PharmacyDto pharmacyDto) {
        User user = new User();
        user.setEmail(pharmacyDto.getEmail());
        user.setPassword(passwordEncoder.encode(pharmacyDto.getPassword()));
        Role role = roleRepository.findByName("PHARMACY");
        user.setRole(role);
        userRepository.save(user);

        PharmacyProfile pharmacyProfile = new PharmacyProfile();
        pharmacyProfile.setName(pharmacyDto.getName());
        pharmacyProfile.setCity(pharmacyDto.getCity());
        pharmacyProfile.setCountry(pharmacyDto.getCountry());
        pharmacyProfile.setAddress(pharmacyDto.getAddress());
        pharmacyProfile.setUser(user);

        MultipartFile file = pharmacyDto.getProfileImage();
        try{
            if(file!=null && !file.isEmpty()){
                pharmacyProfile.setProfileImage(file.getBytes());
            }
            pharmacyProfileRepository.save(pharmacyProfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveDoctorUserAndProfile(DoctorDto doctorDto) {
        User user = new User();
        user.setEmail(doctorDto.getEmail());
        user.setPassword(passwordEncoder.encode(doctorDto.getPassword()));
        Role role = roleRepository.findByName("DOCTOR");
        user.setRole(role);
        userRepository.save(user);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userLoggedIn = userRepository.findByEmail(authentication.getName());

        HospitalProfile hospitalProfile = hospitalProfileRepository.findByUserId(userLoggedIn.getId());

        DoctorProfile doctorProfile = new DoctorProfile();
        doctorProfile.setHospitalProfile(hospitalProfile);
        doctorProfile.setSpecialty(doctorDto.getSpecialty());
        doctorProfile.setBirthday(doctorDto.getBirthday());
        doctorProfile.setNationality(doctorDto.getNationality());
        doctorProfile.setFirstName(doctorDto.getFirstName());
        doctorProfile.setUser(user);
        doctorProfile.setLastName(doctorDto.getLastName());

        MultipartFile file = doctorDto.getProfileImage();
        try{
            if(file!=null && !file.isEmpty()){
                doctorProfile.setProfileImage(file.getBytes());
            }
            doctorProfileRepository.save(doctorProfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
