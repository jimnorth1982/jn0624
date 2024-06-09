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

    private BillableDaysCalendar(boolean billableWeekends) {
        this.billableWeekends = billableWeekends;
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

    public static BillableDaysCalendarBuilder builder(boolean billableWeekends) {
        return new BillableDaysCalendarBuilder(billableWeekends);
    }

    public static BillableDaysCalendarBuilder builder() {
        return new BillableDaysCalendarBuilder(false);
    }

    private static boolean isWeekday(LocalDate inputDate) {
        return inputDate.getDayOfWeek() != DayOfWeek.SATURDAY && inputDate.getDayOfWeek() != DayOfWeek.SUNDAY;
    }

    public static class BillableDaysCalendarBuilder {
        private final BillableDaysCalendar cal;

        public BillableDaysCalendarBuilder(boolean billableWeekends) {
            this.cal = new BillableDaysCalendar(billableWeekends);
        }

        public BillableDaysCalendarBuilder withHoliday(@NonNull LocalDate inputDate) {
            if (this.cal.billableWeekends) {
                this.cal.nonBillableDays.add(inputDate);
            } else {
                switch (inputDate.getDayOfWeek()) {
                    case SATURDAY -> this.cal.nonBillableDays.add(inputDate.minusDays(1));
                    case SUNDAY -> this.cal.nonBillableDays.add(inputDate.plusDays(1));
                    case null -> throw new RuntimeException("LocalDate input for holiday must not be null.");
                    default -> this.cal.nonBillableDays.add(inputDate);
                }
            }
            return this;
        }

        public BillableDaysCalendar build() {
            return cal;
        }
    }
}
