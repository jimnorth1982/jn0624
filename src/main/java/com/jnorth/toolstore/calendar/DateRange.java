package com.jnorth.toolstore.calendar;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

@Data
public class DateRange {
    private final LocalDate checkOutDate;
    private final LocalDate dueDate;

    public DateRange(@NonNull LocalDate checkOutDate, @NonNull LocalDate dueDate) {
        this.checkOutDate = requireNonNull(checkOutDate);
        this.dueDate = requireNonNull(dueDate);
    }

    public DateRange(@NonNull LocalDate checkOutDate, int numberOfDays) {
        this(requireNonNull(checkOutDate), checkOutDate.plusDays(numberOfDays - 1));
    }

    public Stream<LocalDate> stream() {
        return Stream.iterate(checkOutDate, d -> d.plusDays(1))
                .limit(ChronoUnit.DAYS.between(checkOutDate, dueDate) + 1);
    }

    public long totalDays() {
        return ChronoUnit.DAYS.between(checkOutDate, dueDate) + 1;
    }
}
