package com.jnorth.toolstore.invoice.strategy;

import com.jnorth.toolstore.Utils;
import com.jnorth.toolstore.product.ToolType;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ChargeWeekends implements ChargeStrategy {
    @Override
    public BigDecimal apply(@NonNull ToolType toolType, @NonNull LocalDate localDate) {
        if (Utils.isWeekendDay(localDate)) {
            return toolType.dailyRentalCharge();
        }
        return BigDecimal.ZERO;
    }
}
