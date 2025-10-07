package com.antonio.microservicestask.services;

import com.antonio.microservicestask.dto.TrainerWorkloadSummary;
import com.antonio.microservicestask.entities.TrainerWorkload;
import org.springframework.lang.Nullable;

public interface WorkloadService {

    void saveWorkload(TrainerWorkload trainerWorkload);

    @Nullable
    TrainerWorkloadSummary getWorkloadSummary(String username);

    void deleteById(Long id);
}
