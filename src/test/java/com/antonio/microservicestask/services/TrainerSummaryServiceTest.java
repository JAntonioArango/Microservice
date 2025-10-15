package com.antonio.microservicestask.services;

import com.antonio.microservicestask.entities.*;
import com.antonio.microservicestask.repositories.TrainerSummaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TrainerSummaryServiceTest {

    @Mock
    private TrainerSummaryRepository repository;

    @InjectMocks
    private TrainerSummaryService service;

    private TrainerWorkload workload;

    @BeforeEach
    void setUp() {
        workload = new TrainerWorkload();
        workload.setUsername("john.doe");
        workload.setFirstName("John");
        workload.setLastName("Doe");
        workload.setActive(true);
        workload.setTrainingDate(new Date());
        workload.setTrainingDuration(60);
    }

    @Test
    void testProcessTrainingEventNewTrainer() {
        when(repository.findFirstByUsername("john.doe")).thenReturn(Optional.empty());

        service.processTrainingEvent(workload);

        verify(repository).save(any(TrainerSummary.class));
    }

    @Test
    void testProcessTrainingEventExistingTrainer() {
        TrainerSummary existing = new TrainerSummary();
        existing.setUsername("john.doe");
        existing.setYears(new ArrayList<>());

        when(repository.findFirstByUsername("john.doe")).thenReturn(Optional.of(existing));

        service.processTrainingEvent(workload);

        verify(repository).save(existing);
    }

    @Test
    void testUpdateTrainerSummary() {
        List<YearSummary> years = new ArrayList<>();
        
        service.updateTrainerSummary("john.doe", "John", "Doe", true, years);

        verify(repository).updateByUsername("john.doe", "John", "Doe", true, years);
    }
}