package com.licenta.repository;

import com.licenta.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    Schedule findById(int id);
    Schedule findByDayAndStartHourAndEndHour(String day, LocalTime startHour, LocalTime endHour);

    Schedule findByDay(String day);
}
