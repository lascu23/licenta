package com.licenta.service;


import com.licenta.dto.ScheduleDto;
import com.licenta.dto.ScheduleDtoUserAndPhotoDto;
import com.licenta.dto.ScheduleUserAndPhotoDto;
import com.licenta.entity.*;
import com.licenta.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PatientProfileRepository patientProfileRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final PharmacyProfileRepository pharmacyProfileRepository;
    private final HospitalProfileRepository hospitalProfileRepository;
    private final SchedulePharmacyProfileRepository schedulePharmacyProfileRepository;
    private final ScheduleHospitalProfileRepository scheduleHospitalProfileRepository;
    private final ScheduleDoctorProfileRepository scheduleDoctorProfileRepository;
    private final UserServiceImpl userService;
    private final AppointmentRepository appointmentRepository;

    public ProfileServiceImpl(UserRepository userRepo, PatientProfileRepository patientProfRepo,
                              DoctorProfileRepository doctorProfRepo, PharmacyProfileRepository pharmacyProfRepo,
                              HospitalProfileRepository hospitalProfRepo, SchedulePharmacyProfileRepository schedulePharmacyProfRepo,
                              ScheduleHospitalProfileRepository schedHospitalProfRepo, ScheduleDoctorProfileRepository schedDoctorProfRepo, UserServiceImpl userService, AppointmentRepository appointmentRepository) {
        this.userRepository = userRepo;
        this.patientProfileRepository = patientProfRepo;
        this.doctorProfileRepository = doctorProfRepo;
        this.pharmacyProfileRepository = pharmacyProfRepo;
        this.hospitalProfileRepository = hospitalProfRepo;
        this.schedulePharmacyProfileRepository = schedulePharmacyProfRepo;
        this.scheduleHospitalProfileRepository = schedHospitalProfRepo;
        this.scheduleDoctorProfileRepository = schedDoctorProfRepo;
        this.userService = userService;
        this.appointmentRepository = appointmentRepository;
    }


    @Override
    public PatientProfile showLoggedInPatientProfile() {
        User userLoggedIn = userService.getAuthenticationUser();

        PatientProfile patientProfile = patientProfileRepository.findByUserId(userLoggedIn.getId());
        PatientProfile patientProfileNull = new PatientProfile();

        return patientProfile != null ? patientProfile : patientProfileNull;
    }



    @Override
    public void savePatientProfile(PatientProfile patientProfile) {
        PatientProfile existingProfile =patientProfileRepository.findByUserId(userService.getAuthenticationUser().getId());
        if( existingProfile == null){
            patientProfile.setUser(userService.getAuthenticationUser());
            patientProfileRepository.save(patientProfile);
        }else{
            existingProfile.setFirstName(patientProfile.getFirstName());
            existingProfile.setLastName(patientProfile.getLastName());
            existingProfile.setSex(patientProfile.getSex());
            existingProfile.setBirthday(patientProfile.getBirthday());
            existingProfile.setPhoneNumber(patientProfile.getPhoneNumber());
            patientProfileRepository.save(existingProfile);

        }
    }

    @Override
    public void deleteAccount(String email, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        User actualUser = userRepository.findByEmail(email);
        if(patientProfileRepository.findByUserId(actualUser.getId()) != null)
            patientProfileRepository.delete(patientProfileRepository.findByUserId(actualUser.getId()));
        userRepository.delete(userRepository.findByEmail(email));
    }

    private ScheduleDto addScheduleDto(List<Schedule> scheduleList){
        if(!scheduleList.isEmpty()){
            return new ScheduleDto(scheduleList);
        }else{
            ScheduleDto schedulesDto = new ScheduleDto();
            for(int i=1; i <= 7; i++){
                schedulesDto.addSchedule(new Schedule());
            }
            return schedulesDto;
        }
    }

    @Override
    public ScheduleDtoUserAndPhotoDto<?> getProfile(ArrayList<Schedule> schedules) {

        User userLoggedIn = userService.getAuthenticationUser();

        switch (userLoggedIn.getRole().getName()) {
            case "DOCTOR" -> {
                DoctorProfile doctorProfile = doctorProfileRepository.findByUserId(userLoggedIn.getId());

                String base64Image = Base64.getEncoder().encodeToString(doctorProfile.getProfileImage());
                ScheduleDtoUserAndPhotoDto<DoctorProfile> scheduleUserAndPhotoDto = new ScheduleDtoUserAndPhotoDto<>();
                scheduleUserAndPhotoDto.setBase64Image(base64Image);
                scheduleUserAndPhotoDto.setProfile(doctorProfile);

                List<ScheduleDoctorProfile> scheduleDoctorProfiles = scheduleDoctorProfileRepository.findByDoctorProfile(doctorProfile);
                List<Schedule> scheduleList = new ArrayList<>();
                for(ScheduleDoctorProfile scheduleDoctorProfile : scheduleDoctorProfiles){
                    scheduleList.add(scheduleDoctorProfile.getSchedule());
                }

                scheduleUserAndPhotoDto.setScheduleDto(addScheduleDto(scheduleList));
                return scheduleUserAndPhotoDto;
            }
            case "HOSPITAL" -> {
                HospitalProfile hospitalProfile = hospitalProfileRepository.findByUserId(userLoggedIn.getId());
                String base64Image = Base64.getEncoder().encodeToString(hospitalProfile.getProfileImage());
                ScheduleDtoUserAndPhotoDto<HospitalProfile> scheduleUserAndPhotoDto = new ScheduleDtoUserAndPhotoDto<>();
                scheduleUserAndPhotoDto.setBase64Image(base64Image);
                scheduleUserAndPhotoDto.setProfile(hospitalProfile);

                List<ScheduleHospitalProfile> scheduleHospitalProfiles = scheduleHospitalProfileRepository.findByHospitalProfile(hospitalProfile);
                List<Schedule> scheduleList = new ArrayList<>();
                for(ScheduleHospitalProfile scheduleHospitalProfile : scheduleHospitalProfiles){
                    scheduleList.add(scheduleHospitalProfile.getSchedule());
                }

                scheduleUserAndPhotoDto.setScheduleDto(addScheduleDto(scheduleList));


                return scheduleUserAndPhotoDto;
            }
            case "PHARMACY" -> {
                PharmacyProfile pharmacyProfile = pharmacyProfileRepository.findByUserId(userLoggedIn.getId());
                String base64Image = Base64.getEncoder().encodeToString(pharmacyProfile.getProfileImage());
                ScheduleDtoUserAndPhotoDto<PharmacyProfile> scheduleUserAndPhotoDto = new ScheduleDtoUserAndPhotoDto<>();
                scheduleUserAndPhotoDto.setBase64Image(base64Image);
                scheduleUserAndPhotoDto.setProfile(pharmacyProfile);;

                List<SchedulePharmacyProfile> schedulePharmacyProfiles = schedulePharmacyProfileRepository.findByPharmacyProfile(pharmacyProfile);
                List<Schedule> scheduleList = new ArrayList<>();
                for(SchedulePharmacyProfile schedulePharmacyProfile : schedulePharmacyProfiles){
                    scheduleList.add(schedulePharmacyProfile.getSchedule());
                }

                scheduleUserAndPhotoDto.setScheduleDto(addScheduleDto(scheduleList));

                return scheduleUserAndPhotoDto;
            }
        }
        throw new IllegalStateException("Profilul nu poate fi determinat pentru utilizatorul cu id-ul " + userLoggedIn.getId());
    }

    @Override
    public ScheduleUserAndPhotoDto<HospitalProfile> getHospitalProfileFromUserPerspective(String hospital, String address) {
        HospitalProfile hospitalProfile = hospitalProfileRepository.findByNameAndAddress(hospital, address);
        String base64Image = Base64.getEncoder().encodeToString(hospitalProfile.getProfileImage());

        List<Schedule> schedules = scheduleHospitalProfileRepository.findAllSchedulesByHospitalProfileId(hospitalProfile.getId());

        ScheduleUserAndPhotoDto<HospitalProfile> scheduleUserAndPhotoDto = new ScheduleUserAndPhotoDto<>();
        scheduleUserAndPhotoDto.setProfile(hospitalProfile);
        scheduleUserAndPhotoDto.setBase64Image(base64Image);
        scheduleUserAndPhotoDto.setSchedules(schedules);
        return scheduleUserAndPhotoDto;
    }

    @Override
    public ScheduleUserAndPhotoDto<DoctorProfile> getDoctorProfileFromUserPerspective(String firstName, String lastName, String specialty) {
        DoctorProfile doctorProfile = doctorProfileRepository.findByFirstNameAndLastNameAndSpecialty(firstName, lastName, specialty);
        String base64Image = Base64.getEncoder().encodeToString(doctorProfile.getProfileImage());

        List<Schedule> schedules = scheduleDoctorProfileRepository.findAllSchedulesByDoctorProfileId(doctorProfile.getId());

        ScheduleUserAndPhotoDto<DoctorProfile> scheduleUserAndPhotoDto = new ScheduleUserAndPhotoDto<>();
        scheduleUserAndPhotoDto.setProfile(doctorProfile);
        scheduleUserAndPhotoDto.setBase64Image(base64Image);
        scheduleUserAndPhotoDto.setSchedules(schedules);
        return scheduleUserAndPhotoDto;
    }

    @Override
    public ScheduleUserAndPhotoDto<PharmacyProfile> getPharmacyProfileFromUserPerspective(String name, String address) {
        PharmacyProfile pharmacyProfile = pharmacyProfileRepository.findByNameAndAddress(name, address);
        String base64Image = Base64.getEncoder().encodeToString(pharmacyProfile.getProfileImage());

        List<Schedule> schedules = schedulePharmacyProfileRepository.findAllSchedulesByPharmacyProfileId(pharmacyProfile.getId());

        ScheduleUserAndPhotoDto<PharmacyProfile> scheduleUserAndPhotoDto = new ScheduleUserAndPhotoDto<>();
        scheduleUserAndPhotoDto.setProfile(pharmacyProfile);
        scheduleUserAndPhotoDto.setBase64Image(base64Image);
        scheduleUserAndPhotoDto.setSchedules(schedules);
        return scheduleUserAndPhotoDto;
    }
}
