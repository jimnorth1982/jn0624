package com.jnorth.toolstore.calendar;

import lombok.Data;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;

@Data
public class ConsistentMonthDay implements Holiday {
    private MonthDay monthDay;

    public ConsistentMonthDay(MonthDay monthDay) {
        this.monthDay = monthDay;
    }

    @Override
    public LocalDate forYear(Year year) {
        return LocalDate.of(year.getValue(), monthDay.getMonth(), monthDay.getDayOfMonth());
    }
}
