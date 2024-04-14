package com.licenta.dto;

import lombok.Data;

@Data
public class ScheduleDtoUserAndPhotoDto<T>{
    private T profile;
    private String base64Image;
    private ScheduleDto scheduleDto;
}
