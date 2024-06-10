package com.jnorth.toolstore.tools;

import com.jnorth.toolstore.calendar.BillableDaysCalendar;

import java.math.BigDecimal;

public record ToolType(String typeName, BigDecimal dailyRentalCharge, BillableDaysCalendar billableDaysCalendar) {
}
