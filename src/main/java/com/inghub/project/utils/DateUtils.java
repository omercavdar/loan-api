package com.inghub.project.utils;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {

    public static LocalDate getFirstDayOfNextMonth(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfNextMonth());
    }

    public static boolean isWithinNextMonths(LocalDate date, LocalDate referenceDate, int months) {
        LocalDate futureDate = referenceDate.plusMonths(months);
        return !date.isAfter(futureDate);
    }

    public static LocalDate calculateInstallmentDueDate(int installmentNumber, LocalDate loanCreationDate) {
        LocalDate firstDueDate = getFirstDayOfNextMonth(loanCreationDate);
        return firstDueDate.plusMonths(installmentNumber - 1);
    }
}
