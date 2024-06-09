package com.jnorth.toolstore;

import com.jnorth.toolstore.calendar.BillableDaysCalendar;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BillableDaysCalendarTest {

    @Test
    public void createNewBillableDaysCalendar_hasJulyFourth() {
        LocalDate independenceDay = BillableDaysCalendar.exactDate(LocalDate.of(2024, 7, 4));

        BillableDaysCalendar calendar = BillableDaysCalendar.builder()
                .holiday(independenceDay)
                .build();

        assertFalse(calendar.isBillableDay(LocalDate.of(2024, 7, 4)));
        assertTrue(calendar.isBillableDay(LocalDate.of(2024, 7, 5)));
    }

    @Test
    public void createNewBillableDaysCalendar_hasJulyFourthOfJulyOnSaturday() { //friday should be invoiceable
        LocalDate independenceDay = BillableDaysCalendar.exactDate(LocalDate.of(2026, 7, 4));

        BillableDaysCalendar calendar = BillableDaysCalendar.builder()
                .holiday(independenceDay)
                .build();

        assertFalse(calendar.isBillableDay(LocalDate.of(2026, 7, 4)));
        assertFalse(calendar.isBillableDay(LocalDate.of(2026, 7, 3)));
        assertTrue(calendar.isBillableDay(LocalDate.of(2026, 7, 6)));
    }

    @Test
    public void createNewBillableDaysCalendar_hasLaborDay() {
        LocalDate laborDay = BillableDaysCalendar.firstDayOfMonth(YearMonth.of(2024, Month.SEPTEMBER), DayOfWeek.MONDAY);

        BillableDaysCalendar calendar = BillableDaysCalendar.builder()
                .holiday(laborDay)
                .build();

        assertFalse(calendar.isBillableDay(LocalDate.of(2024, 9, 2)));
        assertTrue(calendar.isBillableDay(LocalDate.of(2024, 9, 9)));
    }

    @Test
    public void createNewBillableDaysCalendar_hasCorrectCincoDeMayo() {
        LocalDate cincoDeMayo = BillableDaysCalendar.exactDate(LocalDate.of(2024, 5, 5));

        BillableDaysCalendar calendar = BillableDaysCalendar.builder()
                .holiday(cincoDeMayo)
                .build();

        assertFalse(calendar.isBillableDay(LocalDate.of(2024, 5, 5)));
        assertFalse(calendar.isBillableDay(LocalDate.of(2024, 5, 6)));
    }

    @Test
    public void createNewBillableDaysCalendar_hasCorrectThanksgivingDay() {
        LocalDate thanksgivingDay = BillableDaysCalendar.lastDayOfMonth(YearMonth.of(2024, Month.NOVEMBER), DayOfWeek.THURSDAY);

        BillableDaysCalendar calendar = BillableDaysCalendar.builder()
                .holiday(thanksgivingDay)
                .build();

        assertFalse(calendar.isBillableDay(LocalDate.of(2024, 11, 28)));
        assertTrue(calendar.isBillableDay(LocalDate.of(2024, 11, 21)));
    }

    @Test
    public void createNewBillableDaysCalendar_hasMultipleHolidays() {
        LocalDate thanksgivingDay = BillableDaysCalendar.lastDayOfMonth(YearMonth.of(2024, Month.NOVEMBER), DayOfWeek.THURSDAY);
        LocalDate cincoDeMayo = BillableDaysCalendar.exactDate(LocalDate.of(2024, 5, 5));
        LocalDate laborDay = BillableDaysCalendar.firstDayOfMonth(YearMonth.of(2024, Month.SEPTEMBER), DayOfWeek.MONDAY);
        LocalDate independenceDay = BillableDaysCalendar.exactDate(LocalDate.of(2026, 7, 4));

        BillableDaysCalendar calendar = BillableDaysCalendar.builder()
                .holiday(thanksgivingDay)
                .holiday(cincoDeMayo)
                .holiday(laborDay)
                .holiday(independenceDay)
                .build();

        assertFalse(calendar.isBillableDay(LocalDate.of(2026, 7, 4)));
        assertFalse(calendar.isBillableDay(LocalDate.of(2026, 7, 3)));
        assertTrue(calendar.isBillableDay(LocalDate.of(2026, 7, 6)));

        assertFalse(calendar.isBillableDay(LocalDate.of(2024, 9, 2)));
        assertTrue(calendar.isBillableDay(LocalDate.of(2024, 9, 9)));

        assertFalse(calendar.isBillableDay(LocalDate.of(2024, 5, 5)));
        assertFalse(calendar.isBillableDay(LocalDate.of(2024, 5, 6)));

        assertFalse(calendar.isBillableDay(LocalDate.of(2024, 11, 28)));
        assertTrue(calendar.isBillableDay(LocalDate.of(2024, 11, 21)));
    }
}