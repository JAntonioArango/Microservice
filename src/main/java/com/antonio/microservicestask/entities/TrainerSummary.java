package com.antonio.microservicestask.entities;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@RequiredArgsConstructor
@Document(collection = "trainer_summaries")
public class TrainerSummary implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    
    @NotBlank(message = "Username cannot be null or empty")
    @Indexed(unique = true)
    private String username;
    
    @NotBlank(message = "First name cannot be null or empty")
    private String firstName;
    
    @NotBlank(message = "Last name cannot be null or empty")
    private String lastName;
    
    private Boolean active;
    
    private List<YearSummary> years;
}