package com.jnorth.toolstore.calendar;

import lombok.NonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import static java.time.temporal.TemporalAdjusters.firstInMonth;
import static java.time.temporal.TemporalAdjusters.lastInMonth;

public class BillableDaysCalendar {
    private final Set<LocalDate> nonBillableDays;

    private BillableDaysCalendar() {
        nonBillableDays = new ConcurrentSkipListSet<>();
    }

    public static LocalDate firstDayOfMonth(YearMonth yearMonth, DayOfWeek dayOfWeek) {
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1)
                .with(firstInMonth(dayOfWeek));
    }

    public static LocalDate lastDayOfMonth(YearMonth yearMonth, DayOfWeek dayOfWeek) {
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1)
                .with(lastInMonth(dayOfWeek));
    }

    public static LocalDate exactDate(LocalDate inputDate) {
        return inputDate;
    }

    public static BillableDaysCalendarBuilder builder() {
        return new BillableDaysCalendarBuilder();
    }

    public boolean isBillableDay(LocalDate inputDate) {
        return !nonBillableDays.contains(inputDate) && isWeekday(inputDate);
    }

    private static boolean isWeekday(LocalDate inputDate) {
        return inputDate.getDayOfWeek() != DayOfWeek.SATURDAY && inputDate.getDayOfWeek() != DayOfWeek.SUNDAY;
    }

    public static class BillableDaysCalendarBuilder {
        private final BillableDaysCalendar cal = new BillableDaysCalendar();

        public BillableDaysCalendarBuilder holiday(@NonNull LocalDate inputDate) {
            switch (inputDate.getDayOfWeek()) {
                case SATURDAY -> cal.nonBillableDays.add(inputDate.minusDays(1));
                case SUNDAY -> cal.nonBillableDays.add(inputDate.plusDays(1));
                case null -> throw new RuntimeException("LocalDate input for holiday must not be null.");
                default -> cal.nonBillableDays.add(inputDate);
            }
            return this;
        }

        public BillableDaysCalendar build() {
            return cal;
        }
    }
}
