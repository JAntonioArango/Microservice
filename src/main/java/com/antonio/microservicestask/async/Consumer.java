package com.antonio.microservicestask.async;

import com.antonio.microservicestask.entities.TrainerWorkload;
import com.antonio.microservicestask.services.WorkloadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class Consumer {

    private final WorkloadService workloadService;
    private final Validator validator;

    @JmsListener(destination = "${app.queue}")
    public void onMessage(ObjectMessage objectMessage) {
        try {
            log.info("Received ObjectMessage with JMSMessageID: {}", objectMessage.getJMSMessageID());
            
            TrainerWorkload workload = extractAndValidate(objectMessage);
            processWorkload(workload);
            
            log.info("Successfully processed workload for username: {}", workload.getUsername());
            
        } catch (Exception e) {
            log.error("Error processing ObjectMessage", e);
            throw new RuntimeException("Failed to process workload message", e);
        }
    }

    private TrainerWorkload extractAndValidate(ObjectMessage objectMessage) throws JMSException {
        TrainerWorkload workload = extractObject(objectMessage, TrainerWorkload.class);
        validateWorkload(workload);
        return workload;
    }

    private <T> T extractObject(ObjectMessage objectMessage, Class<T> expectedType) throws JMSException {
        Object messageObject = objectMessage.getObject();
        
        if (messageObject == null) {
            throw new IllegalArgumentException("ObjectMessage contains null object");
        }
        
        if (!expectedType.isInstance(messageObject)) {
            throw new IllegalArgumentException(
                String.format("Expected %s, but received: %s", 
                             expectedType.getSimpleName(), 
                             messageObject.getClass().getName())
            );
        }
        
        return expectedType.cast(messageObject);
    }

    private void validateWorkload(TrainerWorkload workload) {
        Set<ConstraintViolation<TrainerWorkload>> violations = validator.validate(workload);
        
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                .map(ConstraintViolation::getMessage)
                .reduce((m1, m2) -> m1 + ", " + m2)
                .orElse("Validation failed");
            
            throw new IllegalArgumentException("TrainerWorkload validation failed: " + errorMessage);
        }
        
        log.debug("Validation passed for TrainerWorkload: {}", workload.getUsername());
    }

    private void processWorkload(TrainerWorkload workload) {
        log.info("Processing workload for username: {}", workload.getUsername());
        workloadService.saveWorkload(workload);
    }
}