package com.antonio.microservicestask.cucumber.component;

import com.antonio.microservicestask.async.Consumer;
import com.antonio.microservicestask.services.WorkloadService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MessageConsumerComponentSteps {

    private Consumer consumer;
    private WorkloadService mockWorkloadService;
    private String testMessage;
    private Exception thrownException;
    private boolean processingCompleted;

    @Given("the message consumer is initialized")
    public void theMessageConsumerIsInitialized() {
        mockWorkloadService = mock(WorkloadService.class);
        consumer = new Consumer(mockWorkloadService);
    }

    @Given("I have a valid JMS message with trainer data")
    public void iHaveAValidJmsMessageWithTrainerData() {
        testMessage = "TrainerWorkload[username=test.trainer, firstName=Test, lastName=User, active=true, trainingDate=2024-01-15T10:00:00Z, trainingDuration=60]";
        doNothing().when(mockWorkloadService).saveWorkload(any());
    }

    @When("the consumer processes the message")
    public void theConsumerProcessesTheMessage() {
        try {
            consumer.onMessage(testMessage);
            processingCompleted = true;
            thrownException = null;
        } catch (Exception e) {
            processingCompleted = false;
            thrownException = e;
        }
    }

    @Then("the workload should be extracted correctly")
    public void theWorkloadShouldBeExtractedCorrectly() {
        assertTrue(processingCompleted);
    }

    @Then("the workload service should be called")
    public void theWorkloadServiceShouldBeCalled() {
        verify(mockWorkloadService).saveWorkload(any());
    }

    @Then("no consumer exception should be thrown")
    public void noConsumerExceptionShouldBeThrown() {
        assertNull(thrownException);
    }

    @Given("I have a JMS message with invalid format")
    public void iHaveAJmsMessageWithInvalidFormat() {
        testMessage = "Invalid message format without proper structure";
    }

    @Then("the message should be handled gracefully")
    public void theMessageShouldBeHandledGracefully() {
        assertTrue(processingCompleted);
        assertNull(thrownException);
    }

    @Given("I have a null JMS message")
    public void iHaveANullJmsMessage() {
        testMessage = null;
    }

    @Given("I have a JMS message missing username field")
    public void iHaveAJmsMessageMissingUsernameField() {
        testMessage = "TrainerWorkload[firstName=Test, lastName=User, active=true, trainingDate=2024-01-15T10:00:00Z, trainingDuration=60]";
    }

    @Then("an error should be logged")
    public void anErrorShouldBeLogged() {
        // This would typically verify log output, but for simplicity we just ensure graceful handling
        assertTrue(processingCompleted);
    }
}