package com.antonio.microservicestask.entities;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class MonthSummary implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @NotBlank(message = "Month cannot be null or empty")
    private String month;
    
    @NotNull(message = "Total duration cannot be null")
    @PositiveOrZero(message = "Total duration must be zero or positive")
    private Integer totalDuration;
}