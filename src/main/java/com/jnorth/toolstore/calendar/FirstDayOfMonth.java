package com.jnorth.toolstore.calendar;

import com.jnorth.toolstore.Utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

public class FirstDayOfMonth implements Holiday {
    private final Month month;
    private final DayOfWeek dayOfWeek;

    public FirstDayOfMonth(DayOfWeek dayOfWeek, Month month) {
        this.month = month;
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public LocalDate forYear(Year year) {
        return Utils.firstDayOfMonth(month, dayOfWeek, year);
    }
}
