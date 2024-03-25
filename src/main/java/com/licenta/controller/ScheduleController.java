package com.licenta.controller;

import com.licenta.EmbeddedKeys.ScheduleDoctorProfileId;
import com.licenta.EmbeddedKeys.ScheduleHospitalProfileId;
import com.licenta.EmbeddedKeys.SchedulePharmacyProfileId;
import com.licenta.dto.ScheduleDto;
import com.licenta.entity.*;
import com.licenta.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ScheduleController {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PharmacyProfileRepository pharmacyProfileRepository;
    @Autowired
    private SchedulePharmacyProfileRepository schedulePharmacyProfileRepository;
    @Autowired
    private ScheduleHospitalProfileRepository scheduleHospitalProfileRepository;
    @Autowired
    private ScheduleDoctorProfileRepository scheduleDoctorProfileRepository;
    @Autowired
    private DoctorProfileRepository doctorProfileRepository;
    @Autowired
    private HospitalProfileRepository hospitalProfileRepository;

    private Object createScheduleAssociative(Object object, Schedule schedule){
        if(object instanceof PharmacyProfile){
            SchedulePharmacyProfileId idSchedulePharm = new SchedulePharmacyProfileId();
            idSchedulePharm.setPharmacyProfile((PharmacyProfile) object);
            idSchedulePharm.setSchedule(schedule);
            SchedulePharmacyProfile schedulePharmacyProfile = new SchedulePharmacyProfile();
            schedulePharmacyProfile.setId(idSchedulePharm);
            return schedulePharmacyProfile;
        }else if(object instanceof HospitalProfile){
            ScheduleHospitalProfileId idScheduleHospital = new ScheduleHospitalProfileId();
            idScheduleHospital.setHospitalProfile((HospitalProfile) object);
            idScheduleHospital.setSchedule(schedule);
            ScheduleHospitalProfile scheduleHospitalProfile = new ScheduleHospitalProfile();
            scheduleHospitalProfile.setId(idScheduleHospital);
            return scheduleHospitalProfile;
        }else if(object instanceof DoctorProfile){
            ScheduleDoctorProfileId idScheduleDoctor = new ScheduleDoctorProfileId();
            idScheduleDoctor.setDoctorProfile((DoctorProfile) object);
            idScheduleDoctor.setSchedule(schedule);
            ScheduleDoctorProfile scheduleDoctorProfile = new ScheduleDoctorProfile();
            scheduleDoctorProfile.setId(idScheduleDoctor);
            return scheduleDoctorProfile;
        }
        return 0;
    }

    @PostMapping("/schedule/save")
    public String saveSchedules(@ModelAttribute ScheduleDto form, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userLoggedIn = userRepository.findByEmail(authentication.getName());
        PharmacyProfile pharmacyProfile = pharmacyProfileRepository.findByUserId(userLoggedIn.getId());
        DoctorProfile doctorProfile = doctorProfileRepository.findByUserId(userLoggedIn.getId());
        HospitalProfile hospitalProfile = hospitalProfileRepository.findByUserId(userLoggedIn.getId());


        for(Schedule schedule : form.getSchedules()){

            if(pharmacyProfile != null){
                Schedule existingSchedule = schedulePharmacyProfileRepository.findByPharmacyProfileAndScheduleDay(pharmacyProfile.getId(), schedule.getDay());
                if(existingSchedule == null){
                    scheduleRepository.save(schedule);
                    schedulePharmacyProfileRepository.save((SchedulePharmacyProfile) createScheduleAssociative(pharmacyProfile, schedule));
                }else{
                    existingSchedule.setStartHour(schedule.getStartHour());
                    existingSchedule.setEndHour(schedule.getEndHour());
                    scheduleRepository.save(existingSchedule);
                }
            }else if(hospitalProfile != null){
                Schedule existingSchedule = scheduleHospitalProfileRepository.findScheduleByHospitalProfileAndScheduleDay(hospitalProfile.getId(), schedule.getDay());
                if(existingSchedule == null){
                    scheduleRepository.save(schedule);
                    scheduleHospitalProfileRepository.save((ScheduleHospitalProfile) createScheduleAssociative(hospitalProfile, schedule));
                }else{
                    existingSchedule.setStartHour(schedule.getStartHour());
                    existingSchedule.setEndHour(schedule.getEndHour());
                    scheduleRepository.save(existingSchedule);
                }
            }else if(doctorProfile != null){
                Schedule existingSchedule = scheduleDoctorProfileRepository.findByDoctorProfileAndScheduleDay(doctorProfile.getId(), schedule.getDay());
                HospitalProfile hospitalForDoctor = doctorProfile.getHospitalProfile();
                ScheduleHospitalProfile scheduleHospitalProfile = scheduleHospitalProfileRepository.findByHospitalProfileAndScheduleDay(hospitalForDoctor.getId(), schedule.getDay());

                if(existingSchedule == null){
                    if((!schedule.getStartHour().isBefore(scheduleHospitalProfile.getSchedule().getStartHour())) && (!schedule.getEndHour().isAfter(scheduleHospitalProfile.getSchedule().getEndHour()))) {
                        scheduleRepository.save(schedule);
                        scheduleDoctorProfileRepository.save((ScheduleDoctorProfile) createScheduleAssociative(doctorProfile, schedule));
                    }else{
                        List<Schedule> schedules = scheduleDoctorProfileRepository.findAllSchedulesByDoctorProfileId(doctorProfile.getId());
                        List<ScheduleDoctorProfile> scheduleDoctorProfiles = scheduleDoctorProfileRepository.findByDoctorProfile(doctorProfile);
                        if(schedules != null && scheduleDoctorProfiles!=null){
                            scheduleRepository.deleteAll(schedules);
                            scheduleDoctorProfileRepository.deleteAll(scheduleDoctorProfiles);
                        }
                        return "redirect:/profile?wrongHour=true";
                    }
                }else{
                    if((!schedule.getStartHour().isBefore(scheduleHospitalProfile.getSchedule().getStartHour())) && (!schedule.getEndHour().isAfter(scheduleHospitalProfile.getSchedule().getEndHour()))) {
                        existingSchedule.setStartHour(schedule.getStartHour());
                        existingSchedule.setEndHour(schedule.getEndHour());
                        scheduleRepository.save(existingSchedule);
                    }else{
                        List<Schedule> schedules = scheduleDoctorProfileRepository.findAllSchedulesByDoctorProfileId(doctorProfile.getId());
                        List<ScheduleDoctorProfile> scheduleDoctorProfiles = scheduleDoctorProfileRepository.findByDoctorProfile(doctorProfile);
                        if(schedules != null && scheduleDoctorProfiles!=null){
                            scheduleRepository.deleteAll(schedules);
                            scheduleDoctorProfileRepository.deleteAll(scheduleDoctorProfiles);
                        }
                        return "redirect:/profile?wrongHour=true";
                    }
                }

            }
        }
        return "redirect:/profile";
    }

    @GetMapping("/deleteSchedule")
    public String deleteSchedule(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userLoggedIn = userRepository.findByEmail(authentication.getName());
        PharmacyProfile pharmacyProfile = pharmacyProfileRepository.findByUserId(userLoggedIn.getId());
        DoctorProfile doctorProfile = doctorProfileRepository.findByUserId(userLoggedIn.getId());
        HospitalProfile hospitalProfile = hospitalProfileRepository.findByUserId(userLoggedIn.getId());

        if(pharmacyProfile != null){
            List<SchedulePharmacyProfile> schedulePharmacyProfiles = schedulePharmacyProfileRepository.findByPharmacyProfile(pharmacyProfile);
            List<Schedule> schedules = new ArrayList<>();

            for(SchedulePharmacyProfile schedulePharmacyProfile : schedulePharmacyProfiles){
                schedules.add(schedulePharmacyProfile.getSchedule());
                schedulePharmacyProfileRepository.delete(schedulePharmacyProfile);
            }
            scheduleRepository.deleteAll(schedules);
        }else if(doctorProfile != null){
            List<ScheduleDoctorProfile> scheduleDoctorProfiles = scheduleDoctorProfileRepository.findByDoctorProfile(doctorProfile);
            List<Schedule> schedules = new ArrayList<>();
            for(ScheduleDoctorProfile scheduleDoctorProfile : scheduleDoctorProfiles){
                schedules.add(scheduleDoctorProfile.getSchedule());
                scheduleDoctorProfileRepository.delete(scheduleDoctorProfile);
            }
            scheduleRepository.deleteAll(schedules);
        }else if(hospitalProfile != null){
            List<ScheduleHospitalProfile> scheduleHospitalProfiles = scheduleHospitalProfileRepository.findByHospitalProfile(hospitalProfile);
            List<Schedule> schedules = new ArrayList<>();
            for(ScheduleHospitalProfile scheduleHospitalProfile : scheduleHospitalProfiles){
                schedules.add(scheduleHospitalProfile.getSchedule());
                scheduleHospitalProfileRepository.delete(scheduleHospitalProfile);
            }
            scheduleRepository.deleteAll(schedules);
        }
        return "redirect:/profile";
    }
}
