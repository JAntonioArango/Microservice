package com.antonio.microservicestask.controllers;

import com.antonio.microservicestask.dto.TrainerWorkloadSummary;
import com.antonio.microservicestask.entities.TrainerWorkload;
import com.antonio.microservicestask.services.WorkloadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/workload/v1")
@Tag(name = "Workload Management", description = "APIs for managing trainer workloads")
public class WorkloadController {

    @Autowired
    public WorkloadService workloadService;

    @PostMapping("/saveworkload")
    @Operation(summary = "Save trainer workload", description = "Save new trainer workload data")
    @ApiResponse(responseCode = "200", description = "Workload saved successfully")
    public ResponseEntity<TrainerWorkload> saveTrainerWorkload(
            @RequestBody TrainerWorkload trainerWorkload) {

        return ResponseEntity.ok(trainerWorkload);
    }

    @GetMapping("/summary/{username}")
    @Operation(summary = "Get workload summary", description = "Retrieve workload summary for a trainer")
    @ApiResponse(responseCode = "200", description = "Summary retrieved successfully")
    public ResponseEntity<TrainerWorkloadSummary> getTrainerWorkloadSummary(
            @Parameter(description = "Trainer username") @PathVariable String username) {

        return ResponseEntity.ok(workloadService.getWorkloadSummary(username));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete workload", description = "Delete workload record by ID")
    @ApiResponse(responseCode = "200", description = "Workload deleted successfully")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Workload ID") @PathVariable Long id) {
        workloadService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
