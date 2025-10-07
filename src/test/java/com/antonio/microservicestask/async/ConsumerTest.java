package com.antonio.microservicestask.async;

import com.antonio.microservicestask.services.WorkloadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = "app.queue=test.queue")
class ConsumerTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private WorkloadService workloadService;

    @Test
    void testConsumerSavesData() throws InterruptedException {
        String message = "TrainerWorkload[username=junit.test, firstName=JUnit, lastName=Test, active=true, trainingDate=2024-01-15T10:30:00Z, trainingDuration=90]";

        jmsTemplate.convertAndSend("test.queue", message);
        
        Thread.sleep(1000); // Wait for async processing
        
        var summary = workloadService.getWorkloadSummary("junit.testasync");
        assertNotNull(summary);
    }
}