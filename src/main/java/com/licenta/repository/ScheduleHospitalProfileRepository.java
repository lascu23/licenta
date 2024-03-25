package com.licenta.repository;

import com.licenta.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleHospitalProfileRepository extends JpaRepository<ScheduleHospitalProfile, Integer> {
    ScheduleHospitalProfile findByHospitalProfileAndSchedule(HospitalProfile hospitalProfile, Schedule schedule);

    List<ScheduleHospitalProfile> findByHospitalProfile(HospitalProfile hospitalProfile);


    @Query("SELECT sp.schedule FROM ScheduleHospitalProfile sp WHERE sp.hospitalProfile.id = :hospitalProfileId")
    List<Schedule> findAllSchedulesByHospitalProfileId(@Param("hospitalProfileId") int hospitalProfileId);

    @Query("SELECT sp.schedule FROM ScheduleHospitalProfile sp WHERE sp.schedule.day = :scheduleDay")
    Schedule findByScheduleDay(@Param("scheduleDay") String scheduleDay);

    ScheduleHospitalProfile findBySchedule(Schedule schedule);

    @Query("select sp.schedule from ScheduleHospitalProfile sp where sp.schedule.day = :scheduleDay and sp.hospitalProfile.id = :hospitalProfileId")
    Schedule findScheduleByHospitalProfileAndScheduleDay(int hospitalProfileId, String scheduleDay);

    @Query("select sp from ScheduleHospitalProfile sp where sp.schedule.day = :scheduleDay and sp.hospitalProfile.id = :hospitalProfileId")
    ScheduleHospitalProfile findByHospitalProfileAndScheduleDay(int hospitalProfileId, String scheduleDay);

    @Transactional
    @Modifying
    @Query("DELETE FROM ScheduleHospitalProfile shp WHERE shp.schedule IN :schedules")
    void deleteAllBySchedule(List<Schedule> schedules);
}
