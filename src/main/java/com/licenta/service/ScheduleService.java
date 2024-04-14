package com.licenta.service;

import com.licenta.dto.ScheduleDto;

public interface ScheduleService {
    String saveSchedules(ScheduleDto form);

    void deleteSchedule();
}
