package com.ccl;

import java.time.LocalDate;
import java.time.YearMonth;

/**
 * @Description:
 * @Author: ccl
 * @Date: 2022-06-10 16:26
 */
public class Budget {

    private final YearMonth month;

    private final long amount;

    public Budget(YearMonth month, long amount) {
        this.month = month;
        this.amount = amount;
    }

    public LocalDate getStart() {
        return month.atDay(1);
    }

    public LocalDate getEnd() {
        return month.atEndOfMonth();
    }

    public Period getPeriod() {
        return new Period(getStart(),getEnd());
    }

    public long getAmount() {
        return amount;
    }

    public long getDaysCount() {
        return month.lengthOfMonth();
    }

    long getOverlappingAmount(Period period) {
        return amount / getDaysCount() * period.getOverlappingDays(getPeriod());
    }
}
