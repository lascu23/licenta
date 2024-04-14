package com.licenta.dto;

import lombok.Data;

import java.util.List;

@Data
public class ShowCalendarDto {
    private List<List<Integer>> calendar;
    private String currentMonthName;
    private List<String> daysOfWeek;
    private int currentMonth;
    private int currentYear;

    public ShowCalendarDto(List<List<Integer>> calendar, String currentMonthName, List<String> daysOfWeek, int currentMonth, int currentYear) {
        this.calendar = calendar;
        this.currentMonthName = currentMonthName;
        this.daysOfWeek = daysOfWeek;
        this.currentMonth = currentMonth;
        this.currentYear = currentYear;
    }
}
