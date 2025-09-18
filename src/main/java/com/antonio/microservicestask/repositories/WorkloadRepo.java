package com.antonio.microservicestask.repositories;

import com.antonio.microservicestask.entities.TrainerWorkload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkloadRepo extends JpaRepository<TrainerWorkload, Long> {
}
