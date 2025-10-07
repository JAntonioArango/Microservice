package com.antonio.microservicestask.async;

import com.antonio.microservicestask.entities.TrainerWorkload;
import com.antonio.microservicestask.exceptions.MessageExtractionException;
import com.antonio.microservicestask.exceptions.WorkloadProcessingException;
import com.antonio.microservicestask.exceptions.WorkloadValidationException;
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
            
        } catch (MessageExtractionException | WorkloadValidationException e) {
            log.error("Message processing failed: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error processing workload", e);
            throw new WorkloadProcessingException("Failed to process workload message", e);
        }
    }

    private TrainerWorkload extractAndValidate(ObjectMessage objectMessage) throws JMSException {
        TrainerWorkload workload = extractObject(objectMessage);
        validateWorkload(workload);
        return workload;
    }

    private TrainerWorkload extractObject(ObjectMessage objectMessage) throws JMSException {
        Object messageObject = objectMessage.getObject();

        nullChecker(messageObject);

        if (messageObject instanceof TrainerWorkload trainerWorkload) {
            return trainerWorkload;
        }

        throw new MessageExtractionException("Expected TrainerWorkload, but received: " + messageObject.getClass().getSimpleName());
    }

    private void nullChecker(Object messageObject) {
        if (messageObject == null) {
            throw new MessageExtractionException("ObjectMessage contains null object");
        }
    }

    private void validateWorkload(TrainerWorkload workload) {
        Set<ConstraintViolation<TrainerWorkload>> violations = validator.validate(workload);

        if (!violations.isEmpty()) {
            String errorMessage = getString(violations);

            throw new WorkloadValidationException("TrainerWorkload validation failed: " + errorMessage);
        }

        log.debug("Validation passed for TrainerWorkload: {}", workload.getUsername());
    }

    private String getString(Set<ConstraintViolation<TrainerWorkload>> violations) {
        return violations.stream()
            .map(ConstraintViolation::getMessage)
            .reduce((m1, m2) -> m1 + ", " + m2)
            .orElse("Validation failed");
    }

    private void processWorkload(TrainerWorkload workload) {
        log.info("Processing workload for username: {}", workload.getUsername());
        workloadService.saveWorkload(workload);
    }
}