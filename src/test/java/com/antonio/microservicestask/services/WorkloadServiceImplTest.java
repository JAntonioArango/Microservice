package com.antonio.microservicestask.services;

import com.antonio.microservicestask.dto.TrainerWorkloadSummary;
import com.antonio.microservicestask.entities.TrainerWorkload;
import com.antonio.microservicestask.repositories.WorkloadRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkloadServiceImplTest {

    @Mock
    private WorkloadRepo workloadRepo;

    @InjectMocks
    private WorkloadServiceImpl workloadService;

    @Test
    void saveWorkload_ShouldCallRepository() {
        TrainerWorkload workload = new TrainerWorkload();
        workload.setUsername("test");

        workloadService.saveWorkload(workload);

        verify(workloadRepo).save(workload);
    }

    @Test
    void getWorkloadSummary_WithWorkloads_ShouldReturnSummary() {
        TrainerWorkload workload1 = createWorkload("test", "Test", "User", new Date(), 60);
        TrainerWorkload workload2 = createWorkload("test", "Test", "User", new Date(), 90);
        List<TrainerWorkload> workloads = List.of(workload1, workload2);

        when(workloadRepo.findByUsername("test")).thenReturn(workloads);

        TrainerWorkloadSummary result = workloadService.getWorkloadSummary("test");

        assertNotNull(result);
        assertEquals("test", result.getUsername());
        assertEquals("Test", result.getFirstName());
        assertEquals("User", result.getLastName());
        assertEquals(150, result.getTotalDuration());
    }

    @Test
    void getWorkloadSummary_WithEmptyList_ShouldReturnNull() {
        when(workloadRepo.findByUsername("test")).thenReturn(List.of());

        TrainerWorkloadSummary result = workloadService.getWorkloadSummary("test");

        assertNull(result);
    }

    @Test
    void deleteById_ShouldCallRepository() {
        workloadService.deleteById(1L);

        verify(workloadRepo).deleteById(1L);
    }

    private TrainerWorkload createWorkload(String username, String firstName, String lastName, Date date, int duration) {
        TrainerWorkload workload = new TrainerWorkload();
        workload.setUsername(username);
        workload.setFirstName(firstName);
        workload.setLastName(lastName);
        workload.setActive(true);
        workload.setTrainingDate(date);
        workload.setTrainingDuration(duration);
        return workload;
    }
}