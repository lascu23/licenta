package com.licenta.dto;

import com.licenta.entity.Schedule;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScheduleDto {
    private List<Schedule> schedules = new ArrayList<>();

    public ScheduleDto(){}

    public ScheduleDto(List<Schedule> schedules){
        this.schedules = schedules;
    }

    public void addSchedule(Schedule schedule){
        this.schedules.add(schedule);
    }
}
