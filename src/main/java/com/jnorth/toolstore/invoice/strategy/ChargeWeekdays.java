package com.jnorth.toolstore.invoice.strategy;

import com.jnorth.toolstore.product.ToolType;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.jnorth.toolstore.Utils.isWeekday;

public class ChargeWeekdays implements ChargeStrategy {
    @Override
    public BigDecimal apply(@NonNull ToolType toolType, @NonNull LocalDate localDate) {
        if (isWeekday(localDate)) {
            return toolType.dailyRentalCharge();
        }
        return BigDecimal.ZERO;
    }
}
