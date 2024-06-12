package com.jnorth.toolstore.calendar;

import com.jnorth.toolstore.invoice.strategy.ChargeStrategy;

import java.util.SequencedCollection;

public record ChargeSchedule(SequencedCollection<ChargeStrategy> chargeStrategies) {
}
