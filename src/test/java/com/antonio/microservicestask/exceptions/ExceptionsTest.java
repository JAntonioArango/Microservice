package com.antonio.microservicestask.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionsTest {

    @Test
    void testMessageExtractionException() {
        String message = "Test message";
        MessageExtractionException exception = new MessageExtractionException(message);
        
        assertEquals(message, exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void testWorkloadProcessingException() {
        String message = "Processing error";
        Throwable cause = new RuntimeException("Cause");
        WorkloadProcessingException exception = new WorkloadProcessingException(message, cause);
        
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testWorkloadValidationException() {
        String message = "Validation error";
        WorkloadValidationException exception = new WorkloadValidationException(message);
        
        assertEquals(message, exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }
}