package org.anil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class RentalServiceTest {
    private RentalService rentalService;

    @BeforeEach
    public void setup() {
        rentalService = new RentalService(ToolsData.getTools());
    }

    @Test
    public void testCase1() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            rentalService.checkout("JAKR", 5, 101, LocalDate.of(2015, 9, 3));
        });
    }


    @Test
    public void testCase2() {
        RentalAgreement agreement = rentalService.checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2));
        Assertions.assertEquals("LADW", agreement.getTool().getCode());
        Assertions.assertEquals(3, agreement.getRentalDays());
        Assertions.assertEquals(LocalDate.of(2020, 7, 2), agreement.getCheckoutDate());
        Assertions.assertEquals(LocalDate.of(2020, 7, 5), agreement.getDueDate());
        Assertions.assertEquals(3, agreement.getChargeDays());
        Assertions.assertEquals(1.99, agreement.getDailyCharge(), 0.01);
        Assertions.assertEquals(6.00, agreement.getPreDiscountCharge(), 0.01);
        Assertions.assertEquals(10, agreement.getDiscountPercent());
        Assertions.assertEquals(0.50, agreement.getDiscountAmount(), 0.01);
        Assertions.assertEquals(5.50, agreement.getFinalCharge(), 0.01);
    }

    @Test
    public void testCase3() {
        RentalAgreement agreement = rentalService.checkout("CHNS", 5, 25, LocalDate.of(2015, 7, 2));
        Assertions.assertEquals("CHNS", agreement.getTool().getCode());
        Assertions.assertEquals(5, agreement.getRentalDays());
        Assertions.assertEquals(LocalDate.of(2015, 7, 2), agreement.getCheckoutDate());
        Assertions.assertEquals(LocalDate.of(2015, 7, 7), agreement.getDueDate());
        Assertions.assertEquals(3, agreement.getChargeDays()); // 3 chargeable days (7/3, 7/6, 7/7)
        Assertions.assertEquals(1.49, agreement.getDailyCharge(), 0.01);
        Assertions.assertEquals(4.5, agreement.getPreDiscountCharge(), 0.01);
        Assertions.assertEquals(25, agreement.getDiscountPercent());
        Assertions.assertEquals(1.00, agreement.getDiscountAmount(), 0.01);
        Assertions.assertEquals(3.50, agreement.getFinalCharge(), 0.01);
    }

    @Test
    public void testCase4() {
        RentalAgreement agreement = rentalService.checkout("JAKD", 6, 0, LocalDate.of(2015, 9, 3));
        Assertions.assertEquals("JAKD", agreement.getTool().getCode());
        Assertions.assertEquals(6, agreement.getRentalDays());
        Assertions.assertEquals(LocalDate.of(2015, 9, 3), agreement.getCheckoutDate());
        Assertions.assertEquals(LocalDate.of(2015, 9, 9), agreement.getDueDate());
        Assertions.assertEquals(4, agreement.getChargeDays());
        Assertions.assertEquals(2.99, agreement.getDailyCharge(), 0.01);
        Assertions.assertEquals(12.0, agreement.getPreDiscountCharge(), 0.01);
        Assertions.assertEquals(0, agreement.getDiscountPercent());
        Assertions.assertEquals(0.00, agreement.getDiscountAmount(), 0.01);
        Assertions.assertEquals(12.0, agreement.getFinalCharge(), 0.01);
    }

    @Test
    public void testCase5() {
        RentalAgreement agreement = rentalService.checkout("JAKR", 9, 0, LocalDate.of(2015, 7, 2));
        Assertions.assertEquals("JAKR", agreement.getTool().getCode());
        Assertions.assertEquals(9, agreement.getRentalDays());
        Assertions.assertEquals(LocalDate.of(2015, 7, 2), agreement.getCheckoutDate());
        Assertions.assertEquals(LocalDate.of(2015, 7, 11), agreement.getDueDate());
        Assertions.assertEquals(6, agreement.getChargeDays());
        Assertions.assertEquals(2.99, agreement.getDailyCharge(), 0.01);
        Assertions.assertEquals(18.0, agreement.getPreDiscountCharge(), 0.01);
        Assertions.assertEquals(0, agreement.getDiscountPercent());
        Assertions.assertEquals(0.00, agreement.getDiscountAmount(), 0.01);
        Assertions.assertEquals(18.0, agreement.getFinalCharge(), 0.01);
    }

    @Test
    public void testCase6() {
        RentalAgreement agreement = rentalService.checkout("JAKR", 4, 50, LocalDate.of(2020, 7, 2));
        Assertions.assertEquals("JAKR", agreement.getTool().getCode());
        Assertions.assertEquals(4, agreement.getRentalDays());
        Assertions.assertEquals(LocalDate.of(2020, 7, 2), agreement.getCheckoutDate());
        Assertions.assertEquals(LocalDate.of(2020, 7, 6), agreement.getDueDate());
        Assertions.assertEquals(2, agreement.getChargeDays());
        Assertions.assertEquals(2.99, agreement.getDailyCharge(), 0.01);
        Assertions.assertEquals(6.0, agreement.getPreDiscountCharge(), 0.01);
        Assertions.assertEquals(50, agreement.getDiscountPercent());
        Assertions.assertEquals(3.0, agreement.getDiscountAmount(), 0.01);
        Assertions.assertEquals(3.0, agreement.getFinalCharge(), 0.01);
    }


}
