package com.licenta.repository;

import com.licenta.entity.PharmacyProfile;
import com.licenta.entity.Schedule;
import com.licenta.entity.ScheduleHospitalProfile;
import com.licenta.entity.SchedulePharmacyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SchedulePharmacyProfileRepository extends JpaRepository<SchedulePharmacyProfile, Integer> {
    SchedulePharmacyProfile findByPharmacyProfileAndSchedule(PharmacyProfile pharmacyProfile, Schedule schedule);

    List<SchedulePharmacyProfile> findByPharmacyProfile(PharmacyProfile pharmacyProfile);


    @Query("SELECT sp.schedule FROM SchedulePharmacyProfile sp WHERE sp.pharmacyProfile.id = :pharmacyProfileId")
    List<Schedule> findAllSchedulesByPharmacyProfileId(@Param("pharmacyProfileId") int pharmacyProfileId);

    @Query("SELECT sp.schedule FROM SchedulePharmacyProfile sp WHERE sp.schedule.day = :scheduleDay")
    Schedule findByScheduleDay(@Param("scheduleDay") String scheduleDay);

    @Query("select sp.schedule from SchedulePharmacyProfile sp where sp.schedule.day = :scheduleDay and sp.pharmacyProfile.id = :pharmacyProfileId")
    Schedule findByPharmacyProfileAndScheduleDay(int pharmacyProfileId, String scheduleDay);
}
