package com.antonio.microservicestask.exceptions;

public class WorkloadProcessingException extends RuntimeException {
    public WorkloadProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}