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
        LocalDate independenceDay = LocalDate.of(2024, 7, 4);

        BillableDaysCalendar calendar = BillableDaysCalendar.builder()
                .withHoliday(independenceDay)
                .build();

        assertFalse(calendar.isBillableDay(independenceDay));
        assertTrue(calendar.isBillableDay(independenceDay.plusDays(1)));
    }

    @Test
    public void createNewBillableDaysCalendar_hasJulyFourthOfJulyOnSaturday() {
        LocalDate independenceDay = LocalDate.of(2026, 7, 4);

        BillableDaysCalendar calendar = BillableDaysCalendar.builder()
                .withHoliday(independenceDay)
                .build();

        assertFalse(calendar.isBillableDay(independenceDay));
        assertFalse(calendar.isBillableDay(independenceDay.minusDays(1)));
        assertTrue(calendar.isBillableDay(independenceDay.plusDays(2)));
    }

    @Test
    public void createNewBillableDaysCalendar_hasLaborDay() {
        LocalDate laborDay = BillableDaysCalendar.firstDayOfMonth(YearMonth.of(2024, Month.SEPTEMBER), DayOfWeek.MONDAY);

        BillableDaysCalendar calendar = BillableDaysCalendar.builder()
                .withHoliday(laborDay)
                .build();

        LocalDate actualLaborDate = LocalDate.of(2024, 9, 2);

        assertFalse(calendar.isBillableDay(actualLaborDate));
        assertTrue(calendar.isBillableDay(actualLaborDate.plusDays(7)));
    }

    @Test
    public void createNewBillableDaysCalendar_hasCincoDeMayoObservedOnMonday() {
        LocalDate cincoDeMayo = LocalDate.of(2024, 5, 5);

        BillableDaysCalendar calendar = BillableDaysCalendar.builder()
                .withHoliday(cincoDeMayo)
                .build();

        assertFalse(calendar.isBillableDay(cincoDeMayo));
        assertFalse(calendar.isBillableDay(cincoDeMayo.plusDays(1)));
    }

    @Test
    public void createNewBillableDaysCalendar_hasCorrectThanksgivingDay() {
        LocalDate thanksgivingDay = BillableDaysCalendar.lastDayOfMonth(YearMonth.of(2024, Month.NOVEMBER), DayOfWeek.THURSDAY);

        BillableDaysCalendar calendar = BillableDaysCalendar.builder()
                .withHoliday(thanksgivingDay)
                .build();

        LocalDate actualThanksgivingDay = LocalDate.of(2024, 11, 28);

        assertFalse(calendar.isBillableDay(actualThanksgivingDay));
        assertTrue(calendar.isBillableDay(actualThanksgivingDay.minusDays(7)));
    }

    @Test
    public void createNewBillableDaysCalendarWithBillableWeekends_hasJulyFourthOfJulyOnSaturday() {
        LocalDate independenceDayOnSaturday = LocalDate.of(2026, 7, 4);

        BillableDaysCalendar calendar = BillableDaysCalendar.builder(true)
                .withHoliday(independenceDayOnSaturday)
                .build();

        assertFalse(calendar.isBillableDay(independenceDayOnSaturday));
        assertTrue(calendar.isBillableDay(independenceDayOnSaturday.minusDays(1)));
        assertTrue(calendar.isBillableDay(independenceDayOnSaturday.plusDays(2)));
    }

    @Test
    public void createNewBillableDaysCalendar_hasMultipleHolidays() {
        LocalDate independenceDay2024 = LocalDate.of(2024, 7, 4);
        LocalDate independenceDay2026 = LocalDate.of(2026, 7, 4);
        LocalDate thanksgivingDay = BillableDaysCalendar.lastDayOfMonth(YearMonth.of(2024, Month.NOVEMBER), DayOfWeek.THURSDAY);
        LocalDate laborDay = BillableDaysCalendar.firstDayOfMonth(YearMonth.of(2024, Month.SEPTEMBER), DayOfWeek.MONDAY);
        LocalDate cincoDeMayo = LocalDate.of(2024, 5, 5);

        BillableDaysCalendar calendar = BillableDaysCalendar.builder()
                .withHoliday(independenceDay2024)
                .withHoliday(independenceDay2026)
                .withHoliday(thanksgivingDay)
                .withHoliday(cincoDeMayo)
                .withHoliday(laborDay)
                .build();

        assertFalse(calendar.isBillableDay(independenceDay2024));
        assertTrue(calendar.isBillableDay(independenceDay2024.plusDays(1)));

        assertFalse(calendar.isBillableDay(independenceDay2026));
        assertFalse(calendar.isBillableDay(independenceDay2026.minusDays(1)));
        assertTrue(calendar.isBillableDay(independenceDay2026.plusDays(2)));

        assertFalse(calendar.isBillableDay(cincoDeMayo));
        assertFalse(calendar.isBillableDay(cincoDeMayo.plusDays(1)));

        LocalDate actualThanksgivingDay = LocalDate.of(2024, 11, 28);
        assertFalse(calendar.isBillableDay(actualThanksgivingDay));
        assertTrue(calendar.isBillableDay(actualThanksgivingDay.minusDays(7)));

        LocalDate actualLaborDate = LocalDate.of(2024, 9, 2);
        assertFalse(calendar.isBillableDay(actualLaborDate));
        assertTrue(calendar.isBillableDay(actualLaborDate.plusDays(7)));
    }
}