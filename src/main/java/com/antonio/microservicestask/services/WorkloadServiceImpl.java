package com.antonio.microservicestask.services;

import com.antonio.microservicestask.dto.TrainerWorkloadSummary;
import com.antonio.microservicestask.entities.TrainerWorkload;
import com.antonio.microservicestask.repositories.WorkloadRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class WorkloadServiceImpl implements WorkloadService{

    @Autowired
    private WorkloadRepo workloadRepo;

    @Override
    public void saveWorkload(TrainerWorkload trainerWorkload) {
        workloadRepo.save(trainerWorkload);
    }

    @Override
    @Nullable
    public TrainerWorkloadSummary getWorkloadSummary(String username) {
        List<TrainerWorkload> workloads = workloadRepo.findByUsername(username);
        
        if (workloads.isEmpty()) return null;
        
        TrainerWorkload first = workloads.getFirst();
        return new TrainerWorkloadSummary(
            first.getUsername(),
            first.getFirstName(),
            first.getLastName(),
            first.isActive(),
            extractYears(workloads),
            extractMonths(workloads),
            calculateTotalDuration(workloads)
        );
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
        workloadRepo.deleteById(id);
    }

}
