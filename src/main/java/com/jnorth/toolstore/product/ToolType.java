package com.jnorth.toolstore.product;

import com.jnorth.toolstore.calendar.ChargeSchedule;

import java.math.BigDecimal;

public record ToolType(String name, BigDecimal dailyRentalCharge, ChargeSchedule chargeSchedule) {
}
