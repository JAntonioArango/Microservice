package com.antonio.microservicestask.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TrainerSummaryTest {

    private Validator validator;
    private TrainerSummary trainerSummary;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        trainerSummary = new TrainerSummary();
    }

    @Test
    void validate_validTrainerSummary_noViolations() {
        trainerSummary.setUsername("john.doe");
        trainerSummary.setFirstName("John");
        trainerSummary.setLastName("Doe");
        trainerSummary.setActive(true);

        Set<ConstraintViolation<TrainerSummary>> violations = validator.validate(trainerSummary);
        assertTrue(violations.isEmpty());
    }

    @Test
    void validate_blankUsername_violationReturned() {
        trainerSummary.setUsername("");
        trainerSummary.setFirstName("John");
        trainerSummary.setLastName("Doe");

        Set<ConstraintViolation<TrainerSummary>> violations = validator.validate(trainerSummary);
        assertEquals(1, violations.size());
        assertEquals("Username cannot be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    void validate_blankFirstName_violationReturned() {
        trainerSummary.setUsername("john.doe");
        trainerSummary.setFirstName("");
        trainerSummary.setLastName("Doe");

        Set<ConstraintViolation<TrainerSummary>> violations = validator.validate(trainerSummary);
        assertEquals(1, violations.size());
        assertEquals("First name cannot be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    void validate_blankLastName_violationReturned() {
        trainerSummary.setUsername("john.doe");
        trainerSummary.setFirstName("John");
        trainerSummary.setLastName("");

        Set<ConstraintViolation<TrainerSummary>> violations = validator.validate(trainerSummary);
        assertEquals(1, violations.size());
        assertEquals("Last name cannot be null or empty", violations.iterator().next().getMessage());
    }


}
