package com.antonio.microservicestask.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MonthSummaryTest {

    private Validator validator;
    private MonthSummary monthSummary;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        monthSummary = new MonthSummary();
    }

    @Test
    void validate_validMonthSummary_noViolations() {
        monthSummary.setMonth("January");
        monthSummary.setTotalDuration(120);

        Set<ConstraintViolation<MonthSummary>> violations = validator.validate(monthSummary);
        assertTrue(violations.isEmpty());
    }

    @Test
    void validate_blankMonth_violationReturned() {
        monthSummary.setMonth("");
        monthSummary.setTotalDuration(120);

        Set<ConstraintViolation<MonthSummary>> violations = validator.validate(monthSummary);
        assertEquals(1, violations.size());
        assertEquals("Month cannot be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    void validate_nullTotalDuration_violationReturned() {
        monthSummary.setMonth("January");
        monthSummary.setTotalDuration(null);

        Set<ConstraintViolation<MonthSummary>> violations = validator.validate(monthSummary);
        assertEquals(1, violations.size());
        assertEquals("Total duration cannot be null", violations.iterator().next().getMessage());
    }

    @Test
    void validate_negativeTotalDuration_violationReturned() {
        monthSummary.setMonth("January");
        monthSummary.setTotalDuration(-10);

        Set<ConstraintViolation<MonthSummary>> violations = validator.validate(monthSummary);
        assertEquals(1, violations.size());
        assertEquals("Total duration must be zero or positive", violations.iterator().next().getMessage());
    }

    @Test
    void validate_zeroTotalDuration_noViolations() {
        monthSummary.setMonth("January");
        monthSummary.setTotalDuration(0);

        Set<ConstraintViolation<MonthSummary>> violations = validator.validate(monthSummary);
        assertTrue(violations.isEmpty());
    }

}