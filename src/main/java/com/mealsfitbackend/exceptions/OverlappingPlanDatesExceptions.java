package com.mealsfitbackend.exceptions;

import java.time.LocalDate;

public class OverlappingPlanDatesExceptions extends RuntimeException {
    public OverlappingPlanDatesExceptions(LocalDate startDate, LocalDate endDate) {
        super("\n" +
                "There is already a plan for at least one day in this interval: " + startDate + " - " + endDate);
    }
}