package com.antonio.microservicestask.controllers;

import com.antonio.microservicestask.dto.TrainerWorkloadSummary;
import com.antonio.microservicestask.entities.TrainerWorkload;
import com.antonio.microservicestask.services.WorkloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/workload/v1")
public class WorkloadController {

    @Autowired
    public WorkloadService workloadService;

    @PostMapping("/saveworkload")
    public ResponseEntity<TrainerWorkload> saveTrainerWorkload(
            @RequestBody TrainerWorkload trainerWorkload) {

        return ResponseEntity.ok(trainerWorkload);
    }

    @GetMapping("/summary/{username}")
    public ResponseEntity<TrainerWorkloadSummary> getTrainerWorkloadSummary(
            @PathVariable String username) {

        return ResponseEntity.ok(workloadService.getWorkloadSummary(username));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        workloadService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
