package com.licenta.service;

import com.licenta.dto.HospitalProfileDto;
import com.licenta.dto.PharmacyDto;
import com.licenta.dto.RegistrationAndRoleDto;
import com.licenta.dto.RegistrationDto;
import com.licenta.entity.HospitalProfile;
import com.licenta.entity.PharmacyProfile;
import com.licenta.entity.Role;
import com.licenta.entity.User;
import com.licenta.repository.HospitalProfileRepository;
import com.licenta.repository.PharmacyProfileRepository;
import com.licenta.repository.RoleRepository;
import com.licenta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HospitalProfileRepository hospitalProfileRepository;

    @Autowired
    private PharmacyProfileRepository pharmacyProfileRepository;

    @Autowired
    public void UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

//    @Override
//    public void saveHospitalOrPharmacy(RegistrationAndRoleDto registrationAndRoleDto){
//        User user = new User();
//        user.setEmail(registrationAndRoleDto.getEmail());
//        user.setPassword(passwordEncoder.encode((registrationAndRoleDto.getPassword())));
//        Role role = roleRepository.findByName(registrationAndRoleDto.getRole());
//        user.setRole(role);
//        userRepository.save(user);
//    }

//    @Override
//    public void saveHospitalUserAndProfile(HospitalProfileDto hospitalProfileDto){
//        User user = new User();
//        user.setEmail(hospitalProfileDto.getEmail());
//        user.setPassword(passwordEncoder.encode(hospitalProfileDto.getPassword()));
//        Role role = roleRepository.findByName("HOSPITAL");
//        user.setRole(role);
//        userRepository.save(user);
//
//
//        HospitalProfile hospitalProfile = new HospitalProfile();
//        hospitalProfile.setName(hospitalProfileDto.getName());
//        hospitalProfile.setCity(hospitalProfileDto.getCity());
//        hospitalProfile.setCountry(hospitalProfileDto.getCountry());
//        hospitalProfile.setAddress(hospitalProfileDto.getAddress());
//        hospitalProfile.setProfileImage(hospitalProfileDto.getProfileImage());
//        hospitalProfile.setUser(user);
//        hospitalProfileRepository.save(hospitalProfile);
//    }

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
        pharmacyProfile.setProfileImage(pharmacyDto.getProfileImage());
        pharmacyProfile.setUser(user);
        pharmacyProfileRepository.save(pharmacyProfile);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
