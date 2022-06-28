package com.ccl;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Period {
    private final LocalDate start;
    private final LocalDate end;

    public Period(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    long getOverlappingDays(Period another) {
        LocalDate startOfOverlapping = start.isAfter(another.start) ? start : another.start;
        LocalDate endOfOverlapping = end.isBefore(another.end) ? end : another.end;
        return new Period(startOfOverlapping, endOfOverlapping).getDaysCount();
    }

    private long getDaysCount() {
        return getStart().isAfter(getEnd()) ? 0 : getStart().until(getEnd(), DAYS) + 1;
    }

}
