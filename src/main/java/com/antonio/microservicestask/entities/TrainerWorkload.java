package com.antonio.microservicestask.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@Data
@RequiredArgsConstructor
public class TrainerWorkload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private boolean active;
    @Temporal(TemporalType.TIMESTAMP)
    private Date trainingDate;
    private int trainingDuration;
}
