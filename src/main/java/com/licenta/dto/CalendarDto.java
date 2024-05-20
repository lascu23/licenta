package com.licenta.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarDto {
    private int year;
    private int month;
    private int daysInMonth;

    public CalendarDto(int year, int month, int daysInMonth) {
        this.year = year;
        this.month = month;
        this.daysInMonth = daysInMonth;
    }
}
