package com.jnorth.toolstore.product;

import com.jnorth.toolstore.calendar.ChargeSchedule;
import com.jnorth.toolstore.invoice.strategy.ChargeWeekdays;
import com.jnorth.toolstore.invoice.strategy.ChargeWeekends;
import com.jnorth.toolstore.invoice.strategy.NoChargeHolidays;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.jnorth.toolstore.calendar.Holidays.defaultHolidays;

public abstract class ToolsCatalog {

    private static final Map<ToolCode, Tool> catalog = Map.of(
            stihlChainsaw().toolCode(), stihlChainsaw(),
            wernerLadder().toolCode(), wernerLadder(),
            deWaltJackhammer().toolCode(), deWaltJackhammer(),
            ridgidJackhammer().toolCode(), ridgidJackhammer()
    );

    public static Tool get(ToolCode toolCode) {
        return catalog.get(toolCode);
    }

    public static Tool stihlChainsaw() {
        return new Tool(
                new ToolCode("CHNS"),
                chainsaw(),
                new Brand("Stihl")
        );
    }

    private static ToolType chainsaw() {
        return new ToolType("Chainsaw", new BigDecimal("1.49"),
                new ChargeSchedule(List.of(new ChargeWeekdays()), defaultHolidays()));
    }

    private static ToolType ladder() {
        return new ToolType("Ladder", new BigDecimal("1.99"),
                new ChargeSchedule(List.of(new ChargeWeekdays(), new ChargeWeekends(), new NoChargeHolidays()), defaultHolidays()));
    }

    private static ToolType jackhammer() {
        return new ToolType("Jackhammer", new BigDecimal("2.99"),
                new ChargeSchedule(List.of(new ChargeWeekdays(), new NoChargeHolidays()), defaultHolidays()));
    }

    public static Tool wernerLadder() {
        return new Tool(new ToolCode("LADW"), ladder(), new Brand("Werner"));
    }

    public static Tool deWaltJackhammer() {
        return new Tool(
                new ToolCode("JAKD"), jackhammer(), new Brand("DeWalt"));
    }

    public static Tool ridgidJackhammer() {
        return new Tool(new ToolCode("JAKR"), jackhammer(), new Brand("Ridgid"));
    }


}
