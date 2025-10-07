package com.antonio.microservicestask.exceptions;

public class WorkloadValidationException extends RuntimeException {
    public WorkloadValidationException(String message) {
        super(message);
    }
}