package com.antonio.microservicestask.entities;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TrainerWorkloadTest {

    @Test
    void testTrainerWorkloadCreation() {
        TrainerWorkload workload = new TrainerWorkload();
        workload.setId(1L);
        workload.setUsername("test");
        workload.setFirstName("Test");
        workload.setLastName("User");
        workload.setActive(true);
        Date date = new Date();
        workload.setTrainingDate(date);
        workload.setTrainingDuration(60);

        assertEquals(1L, workload.getId());
        assertEquals("test", workload.getUsername());
        assertEquals("Test", workload.getFirstName());
        assertEquals("User", workload.getLastName());
        assertTrue(workload.isActive());
        assertEquals(date, workload.getTrainingDate());
        assertEquals(60, workload.getTrainingDuration());
    }

    @Test
    void testEqualsAndHashCode() {
        TrainerWorkload workload1 = new TrainerWorkload();
        workload1.setUsername("test");
        
        TrainerWorkload workload2 = new TrainerWorkload();
        workload2.setUsername("test");

        assertEquals(workload1, workload2);
        assertEquals(workload1.hashCode(), workload2.hashCode());
    }
}