package com.jnorth.toolstore.invoice;

import com.jnorth.toolstore.Utils;
import com.jnorth.toolstore.calendar.DateRange;
import com.jnorth.toolstore.product.Tool;
import com.jnorth.toolstore.product.ToolType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class RentalAgreement {
    private final Tool tool;
    private final DateRange rentalDateRange;
    private final BigDecimal discount;

    public int chargeDays() {
        Tool tmpTool = new Tool(tool.toolCode(), new ToolType(toolTypeName(), BigDecimal.ONE, tool.toolType().chargeSchedule()), tool.brand());
        return newInstance(tmpTool, rentalDateRange, discount).total().intValue();
    }

    public BigDecimal total() {
        return rentalDateRange.stream()
                .map((date) -> tool.toolType().chargeSchedule().chargeStrategies()
                        .stream()
                        .map(invoiceStrategy -> invoiceStrategy.apply(tool.toolType(), date))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static RentalAgreement newInstance(Tool tool, DateRange dateRange, BigDecimal discount) {
        return RentalAgreement.builder()
                .tool(tool)
                .rentalDateRange(dateRange)
                .discount(discount)
                .build();
    }

    public long totalDays() {
        return rentalDateRange.totalDays();
    }

    public BigDecimal totalWithDiscount() {
        return total().multiply(BigDecimal.ONE.subtract(discount));
    }

    public BigDecimal discountAmount() {
        return total().multiply(discount);
    }

    public void validate() throws ValidationError {
        List<String> messages = new ArrayList<>();
        if (discount.doubleValue() > 1d) {
            messages.add(STR."The discount provided (\{formattedDiscountPercent()}) is greater than %100.");
        }

        if (totalDays() < 1) {
            messages.add(STR."The number of days provided (\{totalDays()}) is less than one day.");
        }

        if (!messages.isEmpty()) {
            throw new ValidationError(String.join("\n", messages));
        }
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
        return Utils.formatDollarAmount(total());
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

    public LocalDate dueDate() {
        return rentalDateRange.getDueDate();
    }

    public LocalDate checkOutDate() {
        return rentalDateRange.getCheckOutDate();
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
