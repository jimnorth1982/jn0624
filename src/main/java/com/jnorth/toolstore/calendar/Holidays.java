package com.jnorth.toolstore.calendar;

import java.time.MonthDay;
import java.util.List;
import java.util.SequencedCollection;

import static java.time.DayOfWeek.MONDAY;
import static java.time.Month.JULY;
import static java.time.Month.SEPTEMBER;

public class Holidays {
    public static SequencedCollection<Holiday> defaultHolidays() {
        return List.of(new ConsistentMonthDay(MonthDay.of(JULY, 4)), new FirstDayOfMonth(MONDAY, SEPTEMBER));
    }
}
