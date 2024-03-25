package com.licenta.controller;

import com.licenta.dto.RegisterPatientDto;
import com.licenta.dto.ScheduleDto;
import com.licenta.entity.*;
import com.licenta.entity.SchedulePharmacyProfile;
import com.licenta.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@Controller
public class ProfilController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientProfileRepository patientProfileRepository;
    @Autowired
    private DoctorProfileRepository doctorProfileRepository;
    @Autowired
    private PharmacyProfileRepository pharmacyProfileRepository;
    @Autowired
    private HospitalProfileRepository hospitalProfileRepository;
    @Autowired
    private SchedulePharmacyProfileRepository schedulePharmacyProfileRepository;
    @Autowired
    private ScheduleHospitalProfileRepository scheduleHospitalProfileRepository;
    @Autowired
    private ScheduleDoctorProfileRepository scheduleDoctorProfileRepository;


    @GetMapping("/profilePatient") //afiseaza profilul pacientului logat
    public String getProfilePatient(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userLoggedIn = userRepository.findByEmail(authentication.getName());

        PatientProfile patientProfile = patientProfileRepository.findByUserId(userLoggedIn.getId());
        PatientProfile patientProfileNull = new PatientProfile();

        if(patientProfile != null){
            model.addAttribute("user", patientProfile);
        }else{
            model.addAttribute("user", patientProfileNull);
        }
        model.addAttribute("userEmail", authentication.getName());
        return "createProfilePatient";
    }

    @PostMapping("/profilePatient/save")//salveaza orice modificare adusa profilului
    public String saveProfilePatient(@ModelAttribute("user") PatientProfile patientProfile){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userLoggedIn = userRepository.findByEmail(authentication.getName());
        PatientProfile existingProfile =patientProfileRepository.findByUserId(userLoggedIn.getId());

        if( existingProfile == null){
            patientProfile.setUser(userLoggedIn);
            patientProfileRepository.save(patientProfile);
        }else{
            existingProfile.setFirstName(patientProfile.getFirstName());
            existingProfile.setLastName(patientProfile.getLastName());
            existingProfile.setSex(patientProfile.getSex());
            existingProfile.setBirthday(patientProfile.getBirthday());
            existingProfile.setPhoneNumber(patientProfile.getPhoneNumber());
            patientProfileRepository.save(existingProfile);

        }

        return "redirect:/home";
    }

    @GetMapping("/deleteAccount") //sterge contul unui pacient
    public String deleteAccount(@RequestParam("userEmail") String email, HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        User actualUser = userRepository.findByEmail(email);
        if(patientProfileRepository.findByUserId(actualUser.getId()) != null)
            patientProfileRepository.delete(patientProfileRepository.findByUserId(actualUser.getId()));
        userRepository.delete(userRepository.findByEmail(email));
        return "redirect:/login?logout=true";
    }

    private void addScheduleDto(List<Schedule> scheduleList, Model model){
        if(!scheduleList.isEmpty()){
            ScheduleDto scheduleDto = new ScheduleDto(scheduleList);
            model.addAttribute("form", scheduleDto);
        }else{
            ScheduleDto schedulesDto = new ScheduleDto();
            for(int i=1; i <= 7; i++){
                schedulesDto.addSchedule(new Schedule());
            }
            model.addAttribute("form", schedulesDto);
        }
    }

    @GetMapping("/profile") //afiseaza profilul unei entitati in functie de logare
    public String getProfile(Model model, @ModelAttribute("schedules") ArrayList<Schedule> schedules){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userLoggedIn = userRepository.findByEmail(authentication.getName());

        switch (userLoggedIn.getRole().getName()) {
            case "DOCTOR" -> {
                DoctorProfile doctorProfile = doctorProfileRepository.findByUserId(userLoggedIn.getId());
                String base64Image = Base64.getEncoder().encodeToString(doctorProfile.getProfileImage());
                model.addAttribute("profilePicture", base64Image);
                model.addAttribute("user", doctorProfile);

                List<ScheduleDoctorProfile> scheduleDoctorProfiles = scheduleDoctorProfileRepository.findByDoctorProfile(doctorProfile);
                List<Schedule> scheduleList = new ArrayList<>();
                for(ScheduleDoctorProfile scheduleDoctorProfile : scheduleDoctorProfiles){
                    scheduleList.add(scheduleDoctorProfile.getSchedule());
                }

                addScheduleDto(scheduleList, model);

                return "profile_doctor";
            }
            case "HOSPITAL" -> {
                HospitalProfile hospitalProfile = hospitalProfileRepository.findByUserId(userLoggedIn.getId());
                String base64Image = Base64.getEncoder().encodeToString(hospitalProfile.getProfileImage());
                model.addAttribute("profilePicture", base64Image);
                model.addAttribute("user", hospitalProfile);

                List<ScheduleHospitalProfile> scheduleHospitalProfiles = scheduleHospitalProfileRepository.findByHospitalProfile(hospitalProfile);
                List<Schedule> scheduleList = new ArrayList<>();
                for(ScheduleHospitalProfile scheduleHospitalProfile : scheduleHospitalProfiles){
                    scheduleList.add(scheduleHospitalProfile.getSchedule());
                }

                addScheduleDto(scheduleList, model);


                return "profile_hospital";
            }
            case "PHARMACY" -> {
                PharmacyProfile pharmacyProfile = pharmacyProfileRepository.findByUserId(userLoggedIn.getId());
                String base64Image = Base64.getEncoder().encodeToString(pharmacyProfile.getProfileImage());
                model.addAttribute("profilePicture", base64Image);
                model.addAttribute("user", pharmacyProfile);

                List<SchedulePharmacyProfile> schedulePharmacyProfiles = schedulePharmacyProfileRepository.findByPharmacyProfile(pharmacyProfile);
                List<Schedule> scheduleList = new ArrayList<>();
                for(SchedulePharmacyProfile schedulePharmacyProfile : schedulePharmacyProfiles){
                    scheduleList.add(schedulePharmacyProfile.getSchedule());
                }

                addScheduleDto(scheduleList, model);

                return "profile_pharmacy";
            }
        }
        throw new IllegalStateException("Profilul nu poate fi determinat pentru utilizatorul cu id-ul " + userLoggedIn.getId());
    }

    @GetMapping("/profileHospital")// din lista tuturor spitalelor, daca apas pe unu ma duce sa i vad pagina de profil
    public String getHospitalProfileFromUserPerspective(@RequestParam("hospital") String hospitalName,@RequestParam String address, Model model){
        HospitalProfile hospitalProfile = hospitalProfileRepository.findByNameAndAddress(hospitalName, address);
        String base64Image = Base64.getEncoder().encodeToString(hospitalProfile.getProfileImage());
        model.addAttribute("profilePicture", base64Image);
        model.addAttribute("user", hospitalProfile);
        return "profile_hospital";
    }

    @GetMapping("/profileDoctor")// din lista tuturor doctorilo, daca apas pe unu ma duce sa i vad pagina de profil
    public String getDoctorProfileFromUserPerspective(@RequestParam String firstName,@RequestParam String lastName,@RequestParam String specialty, Model model){

        DoctorProfile doctorProfile = doctorProfileRepository.findByFirstNameAndLastNameAndSpecialty(firstName, lastName, specialty);
        String base64Image = Base64.getEncoder().encodeToString(doctorProfile.getProfileImage());
        model.addAttribute("profilePicture", base64Image);
        model.addAttribute("user", doctorProfile);
        return "profile_doctor";
    }

    @GetMapping("/profilePharmacy")// din lista tuturor farmaciilor, daca apas pe una ma duce sa i vad pagina de profil
    public String getPharmacyProfileFromUserPerspective(@RequestParam String name,@RequestParam String address, Model model){
        PharmacyProfile pharmacyProfile = pharmacyProfileRepository.findByNameAndAddress(name, address);
        String base64Image = Base64.getEncoder().encodeToString(pharmacyProfile.getProfileImage());

        List<Schedule> schedules = schedulePharmacyProfileRepository.findAllSchedulesByPharmacyProfileId(pharmacyProfile.getId());

        model.addAttribute("schedulesForNotPharmacy", schedules);
        model.addAttribute("profilePicture", base64Image);
        model.addAttribute("user", pharmacyProfile);
        return "profile_pharmacy";
    }
}
