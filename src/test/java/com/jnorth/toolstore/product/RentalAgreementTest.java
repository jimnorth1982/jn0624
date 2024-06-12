package com.jnorth.toolstore.product;

import com.jnorth.toolstore.calendar.DateRange;
import com.jnorth.toolstore.calendar.FirstDayOfMonth;
import com.jnorth.toolstore.invoice.RentalAgreement;
import com.jnorth.toolstore.invoice.ValidationError;
import junit.framework.TestCase;
import org.junit.Assert;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class RentalAgreementTest extends TestCase {

    public void testJAKRInvoiceReport_laborDay_fiveDays_exception() {
        DateRange rentalDateRange = new DateRange(LocalDate.of(2015, 9, 3), 5);
        RentalAgreement rentalAgreement = RentalAgreement.newInstance(
                ToolsCatalog.get(new ToolCode("JAKR")),
                rentalDateRange,
                new BigDecimal("1.01"),
                List.of(new FirstDayOfMonth(DayOfWeek.MONDAY, Month.SEPTEMBER))
        );

        System.out.println(rentalAgreement.print());
        Assert.assertThrows(ValidationError.class, rentalAgreement::validate);
    }

    public void testCHNSInvoiceReport_July4_fiveDays() throws ValidationError {
        RentalAgreement rentalAgreement = RentalAgreement.newInstance(
                ToolsCatalog.get(new ToolCode("CHNS")),
                new DateRange(LocalDate.of(2015, 7, 2), 5),
                new BigDecimal("0.25")
        );

        rentalAgreement.validate();

        assertEquals("Rental period is 5 days", 5, rentalAgreement.totalDays());
        assertEquals("3 chargeable days", 3, rentalAgreement.chargeDays());
        assertEquals("Total is $4.47 before discount", "$4.47", rentalAgreement.formattedTotal());
        assertEquals("Discount is 25%", "25%", rentalAgreement.formattedDiscountPercent());
        assertEquals("Discount is $1.12", "$1.12", rentalAgreement.formattedDiscountAmount());
        assertEquals("Total is $3.35 after discount", "$3.35", rentalAgreement.formattedFinalCharge());

        System.out.println(rentalAgreement.print());
    }



    public void testLADWInvoiceReport_July4_threeDays() throws ValidationError {
        RentalAgreement rep = RentalAgreement.newInstance(
                ToolsCatalog.get(new ToolCode("LADW")),
                new DateRange(LocalDate.of(2020, 7, 2), 3),
                new BigDecimal("0.10")
        );

        rep.validate();

        assertEquals("Rental period is 3 days", 3, rep.totalDays());
        assertEquals("2 chargeable days", 2, rep.chargeDays());
        assertEquals("Total is $3.98 before discount", "$3.98", rep.formattedTotal());
        assertEquals("Discount is 10%", "10%", rep.formattedDiscountPercent());
        assertEquals("Discount is $0.40", "$0.40", rep.formattedDiscountAmount());
        assertEquals("Total is $3.58 after discount", "$3.58", rep.formattedFinalCharge());

        System.out.println(rep.print());
    }

    public void testJAKDInvoiceReport_laborDay_sixDays() throws ValidationError {
        RentalAgreement rentalAgreement = RentalAgreement.newInstance(
                ToolsCatalog.get(new ToolCode("JAKD")),
                new DateRange(LocalDate.of(2015, 9, 3), 6),
                new BigDecimal("0.00")
        );

        rentalAgreement.validate();

        assertEquals("Rental period is 6 days", 6, rentalAgreement.totalDays());
        assertEquals("3 chargeable days", 3, rentalAgreement.chargeDays());
        assertEquals("Total is $8.97 before discount", "$8.97", rentalAgreement.formattedTotal());
        assertEquals("Discount is 0%", "0%", rentalAgreement.formattedDiscountPercent());
        assertEquals("Discount is $0.00", "$0.00", rentalAgreement.formattedDiscountAmount());
        assertEquals("Total is $8.97 after discount", "$8.97", rentalAgreement.formattedFinalCharge());

        System.out.println(rentalAgreement.print());
    }
}
