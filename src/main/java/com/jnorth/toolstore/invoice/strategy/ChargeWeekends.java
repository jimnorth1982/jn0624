package com.jnorth.toolstore.invoice.strategy;

import com.jnorth.toolstore.product.ToolType;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.jnorth.toolstore.Utils.isWeekendDay;
import static java.math.BigDecimal.ZERO;

public class ChargeWeekends implements ChargeStrategy {
    @Override
    public BigDecimal apply(@NonNull ToolType toolType, @NonNull LocalDate localDate) {
        if (isWeekendDay(localDate)) {
            return toolType.dailyRentalCharge();
        }
        return ZERO;
    }
}
