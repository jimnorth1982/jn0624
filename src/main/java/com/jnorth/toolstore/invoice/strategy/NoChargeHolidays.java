package com.jnorth.toolstore.invoice.strategy;

import com.jnorth.toolstore.product.ToolType;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.jnorth.toolstore.Utils.isHoliday;
import static java.math.BigDecimal.ZERO;

public class NoChargeHolidays implements ChargeStrategy {
    @Override
    public BigDecimal apply(@NonNull ToolType toolType, @NonNull LocalDate localDate) {
        if (isHoliday(localDate, toolType.chargeSchedule())) {
            return ZERO.subtract(toolType.dailyRentalCharge());
        }
        return ZERO;
    }
}
