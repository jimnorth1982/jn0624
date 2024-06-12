package com.jnorth.toolstore.invoice.strategy;

import com.jnorth.toolstore.calendar.Holiday;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.SequencedCollection;

@FunctionalInterface
public interface ChargeStrategy {
    BigDecimal apply(@NonNull BigDecimal dailyCharge, @NonNull LocalDate localDate, @NonNull SequencedCollection<Holiday> holidays);
}
