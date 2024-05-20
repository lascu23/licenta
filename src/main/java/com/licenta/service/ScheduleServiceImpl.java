package com.licenta.service;

import com.licenta.EmbeddedKeys.ScheduleDoctorProfileId;
import com.licenta.EmbeddedKeys.ScheduleHospitalProfileId;
import com.licenta.EmbeddedKeys.SchedulePharmacyProfileId;
import com.licenta.dto.ScheduleDto;
import com.licenta.entity.*;
import com.licenta.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final PharmacyProfileRepository pharmacyProfileRepository;
    private final SchedulePharmacyProfileRepository schedulePharmacyProfileRepository;
    private final ScheduleHospitalProfileRepository scheduleHospitalProfileRepository;
    private final ScheduleDoctorProfileRepository scheduleDoctorProfileRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final HospitalProfileRepository hospitalProfileRepository;
    private final UserServiceImpl userService;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, UserRepository userRepository, PharmacyProfileRepository pharmacyProfileRepository, SchedulePharmacyProfileRepository schedulePharmacyProfileRepository, ScheduleHospitalProfileRepository scheduleHospitalProfileRepository, ScheduleDoctorProfileRepository scheduleDoctorProfileRepository, DoctorProfileRepository doctorProfileRepository, HospitalProfileRepository hospitalProfileRepository, UserServiceImpl userService) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
        this.pharmacyProfileRepository = pharmacyProfileRepository;
        this.schedulePharmacyProfileRepository = schedulePharmacyProfileRepository;
        this.scheduleHospitalProfileRepository = scheduleHospitalProfileRepository;
        this.scheduleDoctorProfileRepository = scheduleDoctorProfileRepository;
        this.doctorProfileRepository = doctorProfileRepository;
        this.hospitalProfileRepository = hospitalProfileRepository;
        this.userService = userService;
    }


    @Override
    public String saveSchedules(ScheduleDto form) {

        User userLoggedIn = userService.getAuthenticationUser();
        PharmacyProfile pharmacyProfile = pharmacyProfileRepository.findByUserId(userLoggedIn.getId());
        DoctorProfile doctorProfile = doctorProfileRepository.findByUserId(userLoggedIn.getId());
        HospitalProfile hospitalProfile = hospitalProfileRepository.findByUserId(userLoggedIn.getId());


        for(Schedule schedule : form.getSchedules()){

            if(pharmacyProfile != null){
                Schedule existingSchedule = schedulePharmacyProfileRepository.findByPharmacyProfileAndScheduleDay(pharmacyProfile.getId(), schedule.getDay());
                if(existingSchedule == null){
                    if(schedule.getStartHour()==null || schedule.getEndHour()==null ){
                        schedule.setStartHour(null);
                        schedule.setEndHour(null);
                    }
                    scheduleRepository.save(schedule);
                    schedulePharmacyProfileRepository.save((SchedulePharmacyProfile) createScheduleAssociative(pharmacyProfile, schedule));
                }else{
                    updateToNullSchedule(schedule, existingSchedule);
                }
            }else if(hospitalProfile != null){
                Schedule existingSchedule = scheduleHospitalProfileRepository.findScheduleByHospitalProfileAndScheduleDay(hospitalProfile.getId(), schedule.getDay());
                if(existingSchedule == null){
                    if(schedule.getStartHour()==null || schedule.getEndHour()==null ){
                        schedule.setStartHour(null);
                        schedule.setEndHour(null);
                    }
                    scheduleRepository.save(schedule);
                    scheduleHospitalProfileRepository.save((ScheduleHospitalProfile) createScheduleAssociative(hospitalProfile, schedule));
                }else{
                    updateToNullSchedule(schedule, existingSchedule);
                }
            }else if(doctorProfile != null){
                Schedule existingSchedule = scheduleDoctorProfileRepository.findByDoctorProfileIdAndScheduleDay(doctorProfile.getId(), schedule.getDay());
                HospitalProfile hospitalForDoctor = doctorProfile.getHospitalProfile();
                ScheduleHospitalProfile scheduleHospitalProfile = scheduleHospitalProfileRepository.findByHospitalProfileAndScheduleDay(hospitalForDoctor.getId(), schedule.getDay());

                if(existingSchedule == null){

                    if(schedule.getStartHour()==null || schedule.getEndHour()==null){
                        schedule.setStartHour(null);
                        schedule.setEndHour(null);
                        scheduleRepository.save(schedule);
                        scheduleDoctorProfileRepository.save((ScheduleDoctorProfile) createScheduleAssociative(doctorProfile, schedule));
                    }
                    else if((!schedule.getStartHour().isBefore(scheduleHospitalProfile.getSchedule().getStartHour())) && (!schedule.getEndHour().isAfter(scheduleHospitalProfile.getSchedule().getEndHour()))) {
                        scheduleRepository.save(schedule);
                        scheduleDoctorProfileRepository.save((ScheduleDoctorProfile) createScheduleAssociative(doctorProfile, schedule));
                    }else{
                        List<Schedule> schedules = scheduleDoctorProfileRepository.findAllSchedulesByDoctorProfileId(doctorProfile.getId());
                        List<ScheduleDoctorProfile> scheduleDoctorProfiles = scheduleDoctorProfileRepository.findByDoctorProfile(doctorProfile);
                        if(schedules != null && scheduleDoctorProfiles!=null){
                            scheduleRepository.deleteAll(schedules);
                            scheduleDoctorProfileRepository.deleteAll(scheduleDoctorProfiles);
                        }
                        return "?wrongHour=true";
                    }
                }else{
                    if(schedule.getStartHour()==null || schedule.getEndHour()==null){
                        existingSchedule.setStartHour(null);
                        existingSchedule.setEndHour(null);
                        scheduleRepository.save(existingSchedule);
                        scheduleDoctorProfileRepository.save((ScheduleDoctorProfile) createScheduleAssociative(doctorProfile, existingSchedule));
                    }
                    else if((!schedule.getStartHour().isBefore(scheduleHospitalProfile.getSchedule().getStartHour())) && (!schedule.getEndHour().isAfter(scheduleHospitalProfile.getSchedule().getEndHour()))) {
                        updateToNullSchedule(schedule, existingSchedule);
                    }else{
                        List<Schedule> schedules = scheduleDoctorProfileRepository.findAllSchedulesByDoctorProfileId(doctorProfile.getId());
                        List<ScheduleDoctorProfile> scheduleDoctorProfiles = scheduleDoctorProfileRepository.findByDoctorProfile(doctorProfile);
                        if(schedules != null && scheduleDoctorProfiles!=null){
                            scheduleRepository.deleteAll(schedules);
                            scheduleDoctorProfileRepository.deleteAll(scheduleDoctorProfiles);
                        }
                        return "?wrongHour=true";
                    }
                }

            }
        }
        return "";
    }

    @Override
    public void deleteSchedule() {
        User userLoggedIn = userService.getAuthenticationUser();
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
    }



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

    private void updateToNullSchedule(Schedule schedule, Schedule existingSchedule) {
        if(schedule.getStartHour()==null || schedule.getEndHour()==null ){
            existingSchedule.setStartHour(null);
            existingSchedule.setEndHour(null);
        }else {
            existingSchedule.setStartHour(schedule.getStartHour());
            existingSchedule.setEndHour(schedule.getEndHour());
        }
        scheduleRepository.save(existingSchedule);
    }
}
