package com.licenta.dto;

import com.licenta.entity.Schedule;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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
