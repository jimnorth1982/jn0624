package com.jnorth.toolstore.invoice.strategy;

import com.jnorth.toolstore.calendar.Holiday;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.SequencedCollection;

import static com.jnorth.toolstore.Utils.isHoliday;
import static java.math.BigDecimal.ZERO;

public class NoChargeHolidays implements ChargeStrategy {
    @Override
    public BigDecimal apply(@NonNull BigDecimal dailyCharge, @NonNull LocalDate localDate, @NonNull SequencedCollection<Holiday> holidays) {
        if (isHoliday(localDate, holidays)) {
            return ZERO.subtract(dailyCharge);
        }
        return ZERO;
    }
}
