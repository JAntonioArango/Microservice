package com.antonio.microservicestask.services;

import com.antonio.microservicestask.dto.TrainerWorkloadSummary;
import com.antonio.microservicestask.entities.TrainerWorkload;

public interface WorkloadService {

    TrainerWorkload saveWorkload(TrainerWorkload trainerWorkload);

    TrainerWorkloadSummary getWorkloadSummary(String username);

    void deleteById(Long id);
}
