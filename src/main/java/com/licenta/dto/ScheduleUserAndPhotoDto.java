package com.licenta.dto;

import com.licenta.entity.Schedule;
import lombok.Data;

import java.util.List;

@Data
public class ScheduleUserAndPhotoDto<T> {
    private T profile;
    private String base64Image;
    private List<Schedule> schedules;
}
