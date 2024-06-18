package org.anil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class RentalService {
    private Map<String, Tool> tools;

    public RentalService(Map<String, Tool> tools) {
        this.tools = tools;
    }

    public RentalAgreement checkout(String toolCode, int rentalDays, int discountPercent, LocalDate checkoutDate) {
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental day count must be 1 or greater.");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100.");
        }

        Tool tool = tools.get(toolCode);
        if (tool == null) {
            throw new IllegalArgumentException("Invalid tool code.");
        }

        int chargeDays = calculateChargeDays(tool, checkoutDate, rentalDays);
        double preDiscountCharge = roundToHalfCent(chargeDays * tool.getDailyCharge());
        double discountAmount = roundToHalfCent(preDiscountCharge * discountPercent / 100.0);
        double finalCharge = preDiscountCharge - discountAmount;

        return new RentalAgreement(tool, rentalDays, checkoutDate, chargeDays, preDiscountCharge, discountPercent, discountAmount, finalCharge);
    }

    public double roundToHalfCent(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.multiply(new BigDecimal(2));
        bd = bd.setScale(0, RoundingMode.HALF_UP);
        bd = bd.divide(new BigDecimal(2), 2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private int calculateChargeDays(Tool tool, LocalDate checkoutDate, int rentalDays) {
        int chargeDays = 0;
        LocalDate currentDate = checkoutDate.plusDays(1);

        for (int i = 0; i < rentalDays; i++) {
            boolean isWeekend = currentDate.getDayOfWeek() == java.time.DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == java.time.DayOfWeek.SUNDAY;
            boolean isHoliday = isHoliday(currentDate);

            if ((isWeekend && tool.isWeekendCharge()) || (!isWeekend && tool.isWeekdayCharge()) || (isHoliday && tool.isHolidayCharge())) {
                chargeDays++;
            }
            currentDate = currentDate.plusDays(1);
        }

        return chargeDays;
    }

    private boolean isHoliday(LocalDate date) {
        int year = date.getYear();
        LocalDate independenceDay = LocalDate.of(year, 7, 4);
        LocalDate laborDay = LocalDate.of(year, 9, 1).with(java.time.temporal.TemporalAdjusters.firstInMonth(java.time.DayOfWeek.MONDAY));

        if (independenceDay.getDayOfWeek() == java.time.DayOfWeek.SATURDAY) {
            independenceDay = independenceDay.minusDays(1);
        } else if (independenceDay.getDayOfWeek() == java.time.DayOfWeek.SUNDAY) {
            independenceDay = independenceDay.plusDays(1);
        }

        return date.equals(independenceDay) || date.equals(laborDay);
    }
}
