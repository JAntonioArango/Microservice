package com.antonio.microservicestask.controllers;

import com.antonio.microservicestask.dto.TrainerWorkloadSummary;
import com.antonio.microservicestask.entities.TrainerWorkload;
import com.antonio.microservicestask.services.WorkloadService;
import com.antonio.microservicestask.services.TrainerSummaryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkloadController.class)
class WorkloadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkloadService workloadService;

    @MockBean
    private TrainerSummaryService trainerSummaryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveTrainerWorkload_ShouldReturnWorkload() throws Exception {
        TrainerWorkload workload = new TrainerWorkload();
        workload.setUsername("test");
        workload.setFirstName("Test");
        workload.setLastName("User");
        workload.setActive(true);
        workload.setTrainingDate(new Date());
        workload.setTrainingDuration(60);

        mockMvc.perform(post("/api/workload/v1/saveworkload")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(workload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test"));
    }

    @Test
    void getTrainerWorkloadSummary_ShouldReturnSummary() throws Exception {
        TrainerWorkloadSummary summary = new TrainerWorkloadSummary(
                "test", "Test", "User", true, List.of(2024), List.of("January"), 120);

        when(workloadService.getWorkloadSummary("test")).thenReturn(summary);

        mockMvc.perform(get("/api/workload/v1/summary/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test"))
                .andExpect(jsonPath("$.totalDuration").value(120));
    }

    @Test
    void deleteById_ShouldReturnOk() throws Exception {
        doNothing().when(workloadService).deleteById(1L);

        mockMvc.perform(delete("/api/workload/v1/delete/1"))
                .andExpect(status().isOk());

        verify(workloadService).deleteById(1L);
    }
}