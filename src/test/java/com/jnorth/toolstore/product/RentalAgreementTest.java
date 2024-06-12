package com.jnorth.toolstore.product;

import com.jnorth.toolstore.calendar.DateRange;
import com.jnorth.toolstore.calendar.FirstDayOfMonth;
import com.jnorth.toolstore.invoice.RentalAgreement;
import junit.framework.TestCase;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class RentalAgreementTest extends TestCase {

    public void testJAKR_laborDay_fiveDays_exception() {
        DateRange rentalDateRange = new DateRange(LocalDate.of(2015, 9, 3), 5);
        RentalAgreement rentalAgreement = RentalAgreement.newInstance(
                ToolsCatalog.get(new ToolCode("JAKR")),
                rentalDateRange,
                new BigDecimal("1.01"),
                List.of(new FirstDayOfMonth(DayOfWeek.MONDAY, Month.SEPTEMBER))
        );

        System.out.println(rentalAgreement.print());
        rentalAgreement.validate().forEach(System.out::println);
    }

    public void testCHNS_July4_fiveDays() {
        RentalAgreement rentalAgreement = RentalAgreement.newInstance(
                ToolsCatalog.get(new ToolCode("CHNS")),
                new DateRange(LocalDate.of(2015, 7, 2), 5),
                new BigDecimal("0.25")
        );

        rentalAgreement.validate();
        System.out.println(rentalAgreement.print());
    }

    public void testLADW_July4_threeDays() {
        RentalAgreement rep = RentalAgreement.newInstance(
                ToolsCatalog.get(new ToolCode("LADW")),
                new DateRange(LocalDate.of(2020, 7, 2), 3),
                new BigDecimal("0.10")
        );

        rep.validate();
        System.out.println(rep.print());
    }

    public void testJAKD_laborDay_sixDays() {
        RentalAgreement rentalAgreement = RentalAgreement.newInstance(
                ToolsCatalog.get(new ToolCode("JAKD")),
                new DateRange(LocalDate.of(2015, 9, 3), 6),
                new BigDecimal("0.00")
        );

        rentalAgreement.validate();
        System.out.println(rentalAgreement.print());
    }

    public void testJAKR_July4_nineDays() {
        RentalAgreement rentalAgreement = RentalAgreement.newInstance(
                ToolsCatalog.get(new ToolCode("JAKR")),
                new DateRange(LocalDate.of(2015, 7, 2), 9),
                new BigDecimal("0.00")
        );

        rentalAgreement.validate();
        System.out.println(rentalAgreement.print());
    }

    public void testJAKR_July4_fourDays() {
        RentalAgreement rentalAgreement = RentalAgreement.newInstance(
                ToolsCatalog.get(new ToolCode("JAKR")),
                new DateRange(LocalDate.of(2020, 7, 2), 4),
                new BigDecimal("0.50")
        );

        rentalAgreement.validate();
        System.out.println(rentalAgreement.print());
    }
}
