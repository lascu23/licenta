package com.licenta.repository;

import com.licenta.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.print.Doc;
import java.util.List;

public interface ScheduleDoctorProfileRepository extends JpaRepository<ScheduleDoctorProfile, Integer> {
    ScheduleDoctorProfile findByDoctorProfileAndSchedule(DoctorProfile doctorProfile, Schedule schedule);

    List<ScheduleDoctorProfile> findByDoctorProfile(DoctorProfile doctorProfile);


    @Query("SELECT sp.schedule FROM ScheduleDoctorProfile sp WHERE sp.doctorProfile.id = :doctorProfileId")
    List<Schedule> findAllSchedulesByDoctorProfileId(@Param("doctorProfileId") int doctorProfileId);

    @Query("SELECT sp.schedule FROM ScheduleDoctorProfile sp WHERE sp.schedule.day = :scheduleDay")
    Schedule findByScheduleDay(@Param("scheduleDay") String scheduleDay);

    @Query("select sp.schedule from ScheduleDoctorProfile sp where sp.schedule.day = :scheduleDay and sp.doctorProfile.id = :doctorProfileId")
    Schedule findByDoctorProfileAndScheduleDay(int doctorProfileId, String scheduleDay);

}
