package com.jnorth.toolstore.calendar;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.SequencedCollection;
import java.util.stream.Stream;

import static com.jnorth.toolstore.Utils.firstDayOfMonth;
import static java.time.DayOfWeek.MONDAY;
import static java.time.Month.JULY;
import static java.time.Month.SEPTEMBER;

public class Holidays {
    public static SequencedCollection<LocalDate> defaultHolidays() {
        return holidaysInRange(LocalDate.now().minusYears(20).getYear(), LocalDate.now().plusYears(10).getYear());
    }

    static SequencedCollection<LocalDate> holidaysInRange(int yearFrom, int yearTo) {
        return Stream.iterate(yearFrom, year -> year + 1).limit(yearTo)
                .map(year -> List.of(LocalDate.of(year, JULY, 4),
                        laborDay(year))
                )
                .flatMap(Collection::stream)
                .toList();
    }

    private static LocalDate laborDay(Integer year) {
        return firstDayOfMonth(YearMonth.of(year, SEPTEMBER), MONDAY);
    }
}
