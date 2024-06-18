package org.anil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        RentalService rentalService = new RentalService(ToolsData.getTools());

        try {
            RentalAgreement testcase2 = rentalService.checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2));
            RentalAgreement testcase1 = rentalService.checkout(
                    "JAKR",
                    5,
                    101,
                    LocalDate.of(2015, 9, 3)
            );
            testcase1.print();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
