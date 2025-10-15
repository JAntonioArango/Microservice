package com.antonio.microservicestask.controllers;

import com.antonio.microservicestask.dto.TrainerWorkloadSummary;
import com.antonio.microservicestask.entities.TrainerWorkload;
import com.antonio.microservicestask.entities.TrainerSummary;
import com.antonio.microservicestask.services.WorkloadService;
import com.antonio.microservicestask.services.TrainerSummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/workload/v1")
@Tag(name = "Workload Management", description = "APIs for managing trainer workloads")
public class WorkloadController {

    @Autowired
    public WorkloadService workloadService;
    
    @Autowired
    public TrainerSummaryService trainerSummaryService;

    @PostMapping("/saveworkload")
    @Operation(summary = "Save trainer workload", description = "Save new trainer workload data")
    @ApiResponse(responseCode = "200", description = "Workload saved successfully")
    public ResponseEntity<TrainerWorkload> saveTrainerWorkload(
            @RequestBody TrainerWorkload trainerWorkload) {
        log.info("Transaction started: saveTrainerWorkload for username: {}", trainerWorkload.getUsername());
        log.debug("Operation: Processing workload save request");
        log.info("Transaction completed: saveTrainerWorkload");
        return ResponseEntity.ok(trainerWorkload);
    }

    @GetMapping("/summary/{username}")
    @Operation(summary = "Get workload summary", description = "Retrieve workload summary for a trainer")
    @ApiResponse(responseCode = "200", description = "Summary retrieved successfully")
    public ResponseEntity<TrainerWorkloadSummary> getTrainerWorkloadSummary(
            @Parameter(description = "Trainer username") @PathVariable String username) {
        log.info("Transaction started: getTrainerWorkloadSummary for username: {}", username);
        log.debug("Operation: Retrieving workload summary");
        TrainerWorkloadSummary summary = workloadService.getWorkloadSummary(username);
        log.info("Transaction completed: getTrainerWorkloadSummary");
        return ResponseEntity.ok(summary);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete workload", description = "Delete workload record by ID")
    @ApiResponse(responseCode = "200", description = "Workload deleted successfully")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Workload ID") @PathVariable Long id) {
        log.info("Transaction started: deleteById for id: {}", id);
        log.debug("Operation: Deleting workload record");
        workloadService.deleteById(id);
        log.info("Transaction completed: deleteById");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/process-training")
    @Operation(summary = "Process training event", description = "Process training event and update trainer summary")
    @ApiResponse(responseCode = "200", description = "Training event processed successfully")
    public ResponseEntity<Void> processTrainingEvent(
            @RequestBody TrainerWorkload trainerWorkload) {
        log.info("Transaction started: processTrainingEvent for username: {}", trainerWorkload.getUsername());
        log.debug("Operation: Processing training event");
        trainerSummaryService.processTrainingEvent(trainerWorkload);
        log.info("Transaction completed: processTrainingEvent");
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-summary/{username}")
    @Operation(summary = "Update trainer summary", description = "Update trainer summary by username")
    @ApiResponse(responseCode = "200", description = "Trainer summary updated successfully")
    public ResponseEntity<Void> updateTrainerSummary(
            @Parameter(description = "Trainer username") @PathVariable String username,
            @RequestBody TrainerSummary trainerSummary) {
        log.info("Transaction started: updateTrainerSummary for username: {}", username);
        log.debug("Operation: Updating trainer summary");
        trainerSummaryService.updateTrainerSummary(username, 
                trainerSummary.getFirstName(), 
                trainerSummary.getLastName(), 
                trainerSummary.getActive(), 
                trainerSummary.getYears());
        log.info("Transaction completed: updateTrainerSummary");
        return ResponseEntity.ok().build();
    }

}
