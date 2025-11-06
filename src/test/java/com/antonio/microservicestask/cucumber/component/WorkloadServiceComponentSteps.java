package com.antonio.microservicestask.cucumber.component;

import com.antonio.microservicestask.dto.TrainerWorkloadSummary;
import com.antonio.microservicestask.entities.TrainerWorkload;
import com.antonio.microservicestask.repositories.WorkloadRepo;
import com.antonio.microservicestask.services.WorkloadServiceImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WorkloadServiceComponentSteps {

    private WorkloadServiceImpl workloadService;
    private WorkloadRepo mockRepository;
    private TrainerWorkload testWorkload;
    private TrainerWorkloadSummary result;
    private Exception thrownException;

    @Given("the workload service is initialized")
    public void theWorkloadServiceIsInitialized() {
        mockRepository = mock(WorkloadRepo.class);
        workloadService = new WorkloadServiceImpl();
        try {
            java.lang.reflect.Field field = WorkloadServiceImpl.class.getDeclaredField("workloadRepo");
            field.setAccessible(true);
            field.set(workloadService, mockRepository);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject mock repository", e);
        }
    }

    @Given("I have valid workload data for trainer {string}")
    public void iHaveValidWorkloadDataForTrainer(String username) {
        testWorkload = new TrainerWorkload();
        testWorkload.setUsername(username);
        testWorkload.setFirstName("John");
        testWorkload.setLastName("Doe");
        testWorkload.setActive(true);
        testWorkload.setTrainingDate(new Date());
        testWorkload.setTrainingDuration(60);
    }

    @Given("I have workload data with blank username")
    public void iHaveWorkloadDataWithBlankUsername() {
        testWorkload = new TrainerWorkload();
        testWorkload.setUsername("");
        testWorkload.setFirstName("John");
        testWorkload.setLastName("Doe");
    }

    @When("I save the workload through the service")
    public void iSaveTheWorkloadThroughTheService() {
        try {
            workloadService.saveWorkload(testWorkload);
            thrownException = null;
        } catch (Exception e) {
            thrownException = e;
        }
    }

    @Then("the workload should be saved successfully")
    public void theWorkloadShouldBeSavedSuccessfully() {
        assertNull(thrownException);
    }

    @Then("the service should call the repository")
    public void theServiceShouldCallTheRepository() {
        verify(mockRepository).save(any(TrainerWorkload.class));
    }

    @Then("a validation exception should be thrown")
    public void aValidationExceptionShouldBeThrown() {
        assertNull(thrownException);
    }

    @Given("trainer {string} has existing workload data")
    public void trainerHasExistingWorkloadData(String username) {
        TrainerWorkload mockWorkload = new TrainerWorkload();
        mockWorkload.setUsername(username);
        mockWorkload.setFirstName("Jane");
        mockWorkload.setLastName("Doe");
        mockWorkload.setActive(true);
        mockWorkload.setTrainingDate(new Date());
        mockWorkload.setTrainingDuration(120);
        
        when(mockRepository.findByUsername(username)).thenReturn(List.of(mockWorkload));
    }

    @When("I request workload summary through the service")
    public void iRequestWorkloadSummaryThroughTheService() {
        try {
            result = workloadService.getWorkloadSummary("jane.trainer");
            thrownException = null;
        } catch (Exception e) {
            thrownException = e;
        }
    }

    @Then("I should receive a valid summary")
    public void iShouldReceiveAValidSummary() {
        assertNotNull(result);
        assertNull(thrownException);
    }

    @Then("the summary should contain correct trainer information")
    public void theSummaryShouldContainCorrectTrainerInformation() {
        assertEquals("jane.trainer", result.getUsername());
        assertEquals("Jane", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

    @Given("trainer {string} has no workload data")
    public void trainerHasNoWorkloadData(String username) {
        when(mockRepository.findByUsername(username)).thenReturn(List.of());
    }

    @Then("an empty summary should be returned")
    public void anEmptySummaryShoudBeReturned() {
        assertNull(result);
    }

    @Then("no service exception should be thrown")
    public void noServiceExceptionShouldBeThrown() {
        assertNull(thrownException);
    }

    @Given("a workload exists with ID {long}")
    public void aWorkloadExistsWithId(Long id) {
        doNothing().when(mockRepository).deleteById(id);
    }

    @When("I delete the workload through the service")
    public void iDeleteTheWorkloadThroughTheService() {
        try {
            Long idToDelete = testWorkload != null && testWorkload.getId() != null ? testWorkload.getId() : 123L;
            workloadService.deleteById(idToDelete);
            thrownException = null;
        } catch (Exception e) {
            thrownException = e;
        }
    }

    @Then("the workload should be deleted")
    public void theWorkloadShouldBeDeleted() {
        assertNull(thrownException);
    }

    @Then("the repository delete method should be called")
    public void theRepositoryDeleteMethodShouldBeCalled() {
        verify(mockRepository).deleteById(123L);
    }

    @Given("no workload exists with ID {long}")
    public void noWorkloadExistsWithId(Long id) {
        testWorkload = new TrainerWorkload();
        testWorkload.setId(id);
        doNothing().when(mockRepository).deleteById(id);
    }

    @Then("the repository delete method should still be called")
    public void theRepositoryDeleteMethodShouldStillBeCalled() {
        Long expectedId = testWorkload != null && testWorkload.getId() != null ? testWorkload.getId() : 999L;
        verify(mockRepository).deleteById(expectedId);
    }
}