package com.jnorth.toolstore.invoice.strategy;

import com.jnorth.toolstore.Utils;
import com.jnorth.toolstore.product.ToolType;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class NoChargeHolidays implements ChargeStrategy {
    @Override
    public BigDecimal apply(@NonNull ToolType toolType, @NonNull LocalDate localDate) {
        if (Utils.isHoliday(localDate, toolType.chargeSchedule())) {
            return BigDecimal.ZERO.subtract(toolType.dailyRentalCharge());
        }
        return BigDecimal.ZERO;
    }
}
