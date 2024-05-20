package com.licenta.dto;

import com.licenta.entity.Schedule;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScheduleUserAndPhotoDto<T> {
    private T profile;
    private String base64Image;
    private List<Schedule> schedules;
}
