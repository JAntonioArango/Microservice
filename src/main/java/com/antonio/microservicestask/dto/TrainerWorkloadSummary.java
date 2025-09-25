package com.antonio.microservicestask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TrainerWorkloadSummary {
    private String username;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private List<Integer> years;
    private List<String> months;
    private int totalDuration;
}