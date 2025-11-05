package com.antonio.microservicestask.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class YearSummaryTest {

    private Validator validator;
    private YearSummary yearSummary;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        yearSummary = new YearSummary();
    }

    @Test
    void validate_validYearSummary_noViolations() {
        yearSummary.setYear(2024);
        yearSummary.setMonths(List.of());

        Set<ConstraintViolation<YearSummary>> violations = validator.validate(yearSummary);
        assertTrue(violations.isEmpty());
    }

    @Test
    void validate_nullYear_violationReturned() {
        yearSummary.setYear(null);

        Set<ConstraintViolation<YearSummary>> violations = validator.validate(yearSummary);
        assertEquals(1, violations.size());
        assertEquals("Year cannot be null", violations.iterator().next().getMessage());
    }

}