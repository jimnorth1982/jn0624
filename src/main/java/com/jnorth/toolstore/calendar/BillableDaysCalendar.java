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
    private final boolean billableWeekends;
    private final boolean billableWeekDays;
    private final boolean billableHolidays;

    private BillableDaysCalendar(boolean billableWeekends, boolean billableWeekDays, boolean billableHolidays) {
        this.billableWeekends = billableWeekends;
        this.billableWeekDays = billableWeekDays;
        this.billableHolidays = billableHolidays;
        this.nonBillableDays = new ConcurrentSkipListSet<>();
    }

    public boolean isBillableDay(LocalDate inputDate) {
        return !this.nonBillableDays.contains(inputDate) && (this.billableWeekends || isWeekday(inputDate));
    }

    public static LocalDate firstDayOfMonth(YearMonth yearMonth, DayOfWeek dayOfWeek) {
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1)
                .with(firstInMonth(dayOfWeek));
    }

    public static LocalDate lastDayOfMonth(YearMonth yearMonth, DayOfWeek dayOfWeek) {
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1)
                .with(lastInMonth(dayOfWeek));
    }

    public static BillableDaysCalendarBuilder builder(boolean billableWeekends, boolean billableWeekDays, boolean billableHolidays) {
        return new BillableDaysCalendarBuilder(billableWeekends, billableWeekDays, billableHolidays);
    }

    public static BillableDaysCalendarBuilder builder() {
        return new BillableDaysCalendarBuilder(false, true, false);
    }

    private static boolean isWeekday(LocalDate inputDate) {
        return inputDate.getDayOfWeek() != DayOfWeek.SATURDAY && inputDate.getDayOfWeek() != DayOfWeek.SUNDAY;
    }

    public static class BillableDaysCalendarBuilder {
        private final BillableDaysCalendar cal;
        private final Set<LocalDate> holidays = new ConcurrentSkipListSet<>();

        public BillableDaysCalendarBuilder(boolean billableWeekends, boolean billableWeekDays, boolean billableHolidays) {
            this.cal = new BillableDaysCalendar(billableWeekends, billableWeekDays, billableHolidays);
        }

        public BillableDaysCalendarBuilder withHoliday(@NonNull LocalDate inputDate) {
            holidays.add(inputDate);
            return this;
        }

        public BillableDaysCalendar build() {
            for (LocalDate inputDate : this.holidays) {
                switch (inputDate.getDayOfWeek()) {
                    case SATURDAY -> {
                        if (this.cal.billableWeekends) {
                            this.cal.nonBillableDays.add(inputDate);
                        } else {
                            this.cal.nonBillableDays.add(inputDate.minusDays(1));
                        }
                    }
                    case SUNDAY -> {
                        if (this.cal.billableWeekends) {
                            this.cal.nonBillableDays.add(inputDate);
                        } else {
                            this.cal.nonBillableDays.add(inputDate.plusDays(1));
                        }
                    }
                    case null -> throw new RuntimeException("LocalDate input for holiday must not be null.");
                    default -> this.cal.nonBillableDays.add(inputDate);
                }
            }
            return cal;
        }
    }
}
