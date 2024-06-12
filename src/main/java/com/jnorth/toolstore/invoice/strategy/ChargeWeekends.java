package com.jnorth.toolstore.invoice.strategy;

import com.jnorth.toolstore.calendar.Holiday;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.SequencedCollection;

import static com.jnorth.toolstore.Utils.isWeekendDay;
import static java.math.BigDecimal.ZERO;

public class ChargeWeekends implements ChargeStrategy {
    @Override
    public BigDecimal apply(@NonNull BigDecimal dailyCharge, @NonNull LocalDate localDate, @NonNull SequencedCollection<Holiday> holidays) {
        if (isWeekendDay(localDate)) {
            return dailyCharge;
        }
        return ZERO;
    }
}
