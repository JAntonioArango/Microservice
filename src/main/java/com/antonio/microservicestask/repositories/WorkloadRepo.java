package com.antonio.microservicestask.repositories;

import com.antonio.microservicestask.entities.TrainerWorkload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkloadRepo extends JpaRepository<TrainerWorkload, Long> {
    List<TrainerWorkload> findByUsername(String username);
}
