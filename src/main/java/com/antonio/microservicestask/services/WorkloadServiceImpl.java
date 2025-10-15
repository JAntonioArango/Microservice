package com.antonio.microservicestask.services;

import com.antonio.microservicestask.dto.TrainerWorkloadSummary;
import com.antonio.microservicestask.entities.TrainerWorkload;
import com.antonio.microservicestask.repositories.WorkloadRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WorkloadServiceImpl implements WorkloadService{

    @Autowired
    private WorkloadRepo workloadRepo;

    @Override
    public void saveWorkload(TrainerWorkload trainerWorkload) {
        log.debug("Operation: Saving workload for username: {}", trainerWorkload.getUsername());
        workloadRepo.save(trainerWorkload);
        log.debug("Operation completed: Workload saved");
    }

    @Override
    @Nullable
    public TrainerWorkloadSummary getWorkloadSummary(String username) {
        log.debug("Operation: Getting workload summary for username: {}", username);
        List<TrainerWorkload> workloads = workloadRepo.findByUsername(username);
        
        if (workloads.isEmpty()) {
            log.debug("Operation completed: No workloads found for username: {}", username);
            return null;
        }
        
        TrainerWorkload first = workloads.getFirst();
        TrainerWorkloadSummary summary = new TrainerWorkloadSummary(
            first.getUsername(),
            first.getFirstName(),
            first.getLastName(),
            first.isActive(),
            extractYears(workloads),
            extractMonths(workloads),
            calculateTotalDuration(workloads)
        );
        log.debug("Operation completed: Workload summary created");
        return summary;
    }
    
    private List<Integer> extractYears(List<TrainerWorkload> workloads) {
        return workloads.stream()
            .map(w -> w.getTrainingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear())
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }
    
    private List<String> extractMonths(List<TrainerWorkload> workloads) {
        return workloads.stream()
            .map(w -> w.getTrainingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH))
            .distinct()
            .collect(Collectors.toList());
    }
    
    private int calculateTotalDuration(List<TrainerWorkload> workloads) {
        return workloads.stream()
            .mapToInt(TrainerWorkload::getTrainingDuration)
            .sum();
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Operation: Deleting workload with id: {}", id);
        workloadRepo.deleteById(id);
        log.debug("Operation completed: Workload deleted");
    }

}
