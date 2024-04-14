package com.licenta.dto;

import lombok.Data;

@Data
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
