package com.jnorth.toolstore.invoice.strategy;

import com.jnorth.toolstore.product.ToolType;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@FunctionalInterface
public interface ChargeStrategy {
    BigDecimal apply(@NonNull ToolType toolType, @NonNull LocalDate localDate);
}
