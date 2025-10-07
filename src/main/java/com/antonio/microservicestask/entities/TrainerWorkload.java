package com.antonio.microservicestask.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@RequiredArgsConstructor
public class TrainerWorkload implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be null or empty")
    private String username;

    @NotBlank(message = "First name cannot be null or empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be null or empty")
    private String lastName;

    private boolean active;

    @Temporal(TemporalType.TIMESTAMP)
    private Date trainingDate;

    @NotNull(message = "Training duration cannot be null")
    @Positive(message = "Training duration must be a positive number")
    private int trainingDuration;

}
