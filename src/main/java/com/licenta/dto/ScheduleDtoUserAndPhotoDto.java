package com.licenta.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDtoUserAndPhotoDto<T>{
    private T profile;
    private String base64Image;
    private ScheduleDto scheduleDto;
}
