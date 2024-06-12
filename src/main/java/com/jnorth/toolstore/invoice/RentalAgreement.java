package com.jnorth.toolstore.invoice;

import com.jnorth.toolstore.Utils;
import com.jnorth.toolstore.calendar.DateRange;
import com.jnorth.toolstore.calendar.Holiday;
import com.jnorth.toolstore.product.Tool;
import com.jnorth.toolstore.product.ToolType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.SequencedCollection;

import static com.jnorth.toolstore.calendar.Holidays.defaultHolidays;

@Data
@Builder
public class RentalAgreement {
    private final Tool tool;
    private final DateRange rentalDateRange;
    private final BigDecimal discount;
    private final SequencedCollection<Holiday> holidays;

    public int chargeDays() {
        Tool tmpTool = new Tool(tool.toolCode(), new ToolType(toolTypeName(), BigDecimal.ONE, tool.toolType().chargeSchedule()), tool.brand());
        return newInstance(tmpTool, rentalDateRange, discount).defaultHolidayTotal().intValue();
    }

    public BigDecimal defaultHolidayTotal() {
        return total(defaultHolidays());
    }

    public BigDecimal total(SequencedCollection<Holiday> holidays) {
        return rentalDateRange.stream()
                .map((date) -> tool.toolType().chargeSchedule().chargeStrategies()
                        .stream()
                        .map(invoiceStrategy -> invoiceStrategy.apply(tool.toolType().dailyRentalCharge(), date, holidays))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static RentalAgreement newInstance(Tool tool, DateRange dateRange, BigDecimal discount) {
        return newInstance(tool, dateRange, discount, defaultHolidays());
    }

    public static RentalAgreement newInstance(Tool tool, DateRange dateRange, BigDecimal discount, SequencedCollection<Holiday> holidays) {
        return RentalAgreement.builder()
                .tool(tool)
                .rentalDateRange(dateRange)
                .discount(discount)
                .holidays(holidays)
                .build();
    }

    public long totalDays() {
        return rentalDateRange.totalDays();
    }

    public BigDecimal totalWithDiscount() {
        return total(holidays).multiply(BigDecimal.ONE.subtract(discount));
    }

    public BigDecimal discountAmount() {
        return total(holidays).multiply(discount);
    }

    public List<String> validate() {
        List<String> messages = new ArrayList<>();
        if (discount.doubleValue() > 1d) {
            messages.add(STR."The discount provided (\{formattedDiscountPercent()}) is greater than %100.");
        }

        if (totalDays() < 1) {
            messages.add(STR."The number of days provided (\{totalDays()}) is less than one day.");
        }

        return messages;
    }

    public String print() {
        return STR."""
        Tool code:              \{toolCodeName()}
        Tool type:              \{toolTypeName()}
        Tool brand:             \{brandName()}
        Rental days:            \{totalDays()}
        Checkout date:          \{checkOutDate()}
        Due date:               \{dueDate()}
        Daily rental charge:    \{formattedDailyRentalCharge()}
        Charge days:            \{chargeDays()}
        Pre-discount charge:    \{formattedTotal()}
        Discount percent:       \{formattedDiscountPercent()}
        Discount amount:        \{formattedDiscountAmount()}
        Final charge:           \{formattedFinalCharge()}
        """;
    }

    public String formattedTotal() {
        return Utils.formatDollarAmount(total(holidays));
    }

    public String formattedFinalCharge() {
        return Utils.formatDollarAmount(totalWithDiscount());
    }

    public String formattedDiscountAmount() {
        return Utils.formatDollarAmount(discountAmount());
    }

    public String formattedDiscountPercent() {
        return Utils.formatPercentage(discount);
    }

    public String formattedDailyRentalCharge() {
        return Utils.formatDollarAmount(tool.toolType().dailyRentalCharge());
    }

    public String dueDate() {
        return Utils.formatDate(rentalDateRange.getDueDate());
    }

    public String checkOutDate() {
        return Utils.formatDate(rentalDateRange.getCheckOutDate());
    }

    public String brandName() {
        return tool.brand().name();
    }

    public String toolTypeName() {
        return tool.toolType().name();
    }

    public String toolCodeName() {
        return tool.toolCode().code();
    }
}
