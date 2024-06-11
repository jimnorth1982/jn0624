package com.jnorth.toolstore;

import com.jnorth.toolstore.calendar.ChargeSchedule;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Locale;

import static java.text.NumberFormat.getCurrencyInstance;
import static java.text.NumberFormat.getPercentInstance;
import static java.time.LocalDate.of;
import static java.time.temporal.TemporalAdjusters.firstInMonth;
import static java.time.temporal.TemporalAdjusters.lastInMonth;

public class Utils {
    public static boolean isWeekday(@NonNull LocalDate localDate) {
        return localDate.getDayOfWeek() != DayOfWeek.SATURDAY && localDate.getDayOfWeek() != DayOfWeek.SUNDAY;
    }

    public static boolean isWeekendDay(@NonNull LocalDate localDate) {
        return !isWeekday(localDate);
    }

    public static boolean isHoliday(@NonNull LocalDate localDate, @NonNull ChargeSchedule chargeSchedule) {
        return chargeSchedule.holidays().contains(localDate);
    }

    public static LocalDate firstDayOfMonth(YearMonth yearMonth, DayOfWeek dayOfWeek) {
        return of(yearMonth.getYear(), yearMonth.getMonth(), 1).with(firstInMonth(dayOfWeek));
    }

    public static LocalDate lastDayOfMonth(YearMonth yearMonth, DayOfWeek dayOfWeek) {
        return of(yearMonth.getYear(), yearMonth.getMonth(), 1).with(lastInMonth(dayOfWeek));
    }

    public static String formatDollarAmount(BigDecimal dollarAmount) {
        return getCurrencyInstance(Locale.US).format(dollarAmount.setScale(2, RoundingMode.HALF_UP));
    }

    public static String formatPercentage(BigDecimal percentage) {
        return getPercentInstance(Locale.US).format(percentage);
    }
}
