package com.jnorth.toolstore;

import com.jnorth.toolstore.calendar.Holiday;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.SequencedCollection;

import static java.math.RoundingMode.HALF_UP;
import static java.text.NumberFormat.getCurrencyInstance;
import static java.text.NumberFormat.getPercentInstance;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.LocalDate.of;
import static java.time.temporal.TemporalAdjusters.firstInMonth;

public class Utils {
    public static boolean isWeekday(@NonNull LocalDate localDate) {
        return localDate.getDayOfWeek() != SATURDAY && localDate.getDayOfWeek() != SUNDAY;
    }

    public static boolean isWeekendDay(@NonNull LocalDate localDate) {
        return !isWeekday(localDate);
    }

    public static boolean isHoliday(@NonNull LocalDate localDate, @NonNull SequencedCollection<Holiday> holidays) {
        return holidays.stream()
                .map(holiday -> holiday.forYear(Year.of(localDate.getYear())))
                .anyMatch(localDate::equals);
    }

    public static LocalDate firstDayOfMonth(Month month, DayOfWeek dayOfWeek, Year year) {
        return of(year.getValue(), month, 1).with(firstInMonth(dayOfWeek));
    }

    public static String formatDollarAmount(BigDecimal dollarAmount) {
        return getCurrencyInstance(Locale.US).format(dollarAmount.setScale(2, HALF_UP));
    }

    public static String formatPercentage(BigDecimal percentage) {
        return getPercentInstance(Locale.US).format(percentage);
    }

    public static String formatDate(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("MM/dd/yy"));
    }
}
