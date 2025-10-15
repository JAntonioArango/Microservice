package com.antonio.microservicestask.entities;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@RequiredArgsConstructor
public class YearSummary implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @NotNull(message = "Year cannot be null")
    private Integer year;
    
    private List<MonthSummary> months;
}