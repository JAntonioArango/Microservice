package com.antonio.microservicestask.cucumber.integration;

import com.antonio.microservicestask.entities.TrainerWorkload;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class WorkloadApiIntegrationSteps {

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    private ResponseEntity<String> response;
    private String baseUrl;

    @Given("the workload service is running")
    public void theWorkloadServiceIsRunning() {
        baseUrl = "http://localhost:" + port;
    }

    @When("I send a POST request to {string} with:")
    public void iSendAPostRequestToWith(String endpoint, DataTable dataTable) {
        Map<String, String> data = dataTable.asMap();
        
        TrainerWorkload workload = new TrainerWorkload();
        workload.setUsername(data.get("username"));
        workload.setFirstName(data.get("firstName"));
        workload.setLastName(data.get("lastName"));
        workload.setActive(Boolean.parseBoolean(data.get("active")));
        try {
            workload.setTrainingDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(data.get("trainingDate")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        workload.setTrainingDuration(Integer.parseInt(data.get("trainingDuration")));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<TrainerWorkload> request = new HttpEntity<>(workload, headers);

        response = restTemplate.postForEntity(baseUrl + endpoint, request, String.class);
    }

    @When("I send a GET request to {string}")
    public void iSendAGetRequestTo(String endpoint) {
        response = restTemplate.getForEntity(baseUrl + endpoint, String.class);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedStatus) {
        assertEquals(expectedStatus, response.getStatusCode().value());
    }

    @Given("a trainer {string} has workload data:")
    public void aTrainerHasWorkloadData(String username, DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        
        for (Map<String, String> row : rows) {
            TrainerWorkload workload = new TrainerWorkload();
            workload.setUsername(username);
            workload.setFirstName(row.get("firstName"));
            workload.setLastName(row.get("lastName"));
            workload.setActive(Boolean.parseBoolean(row.get("active")));
            try {
                workload.setTrainingDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(row.get("trainingDate")));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            workload.setTrainingDuration(Integer.parseInt(row.get("trainingDuration")));

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<TrainerWorkload> request = new HttpEntity<>(workload, headers);
            restTemplate.postForEntity(baseUrl + "/api/workload/v1/saveworkload", request, String.class);
        }
    }

    @Then("the response should contain:")
    public void theResponseShouldContain(DataTable dataTable) throws Exception {
        Map<String, String> expected = dataTable.asMap();
        
        assertNotNull(response.getBody(), "Response body should not be null");
        Map<String, Object> actual = objectMapper.readValue(response.getBody(), Map.class);

        for (Map.Entry<String, String> entry : expected.entrySet()) {
            String key = entry.getKey();
            String expectedValue = entry.getValue();
            
            if ("active".equals(key)) {
                assertEquals(Boolean.parseBoolean(expectedValue), actual.get(key));
            } else {
                assertEquals(expectedValue, actual.get(key));
            }
        }
    }

    @Given("a trainer {string} has multiple workload entries:")
    public void aTrainerHasMultipleWorkloadEntries(String username, DataTable dataTable) {
        aTrainerHasWorkloadData(username, dataTable);
    }

    @Given("a trainer {string} has workload data with status changes:")
    public void aTrainerHasWorkloadDataWithStatusChanges(String username, DataTable dataTable) {
        aTrainerHasWorkloadData(username, dataTable);
    }

    @Then("the summary should show total duration of {int}")
    public void theSummaryShouldShowTotalDurationOf(int expectedDuration) throws Exception {
        assertNotNull(response.getBody(), "Response body should not be null");
        Map<String, Object> actual = objectMapper.readValue(response.getBody(), Map.class);
        assertEquals(expectedDuration, actual.get("totalDuration"));
    }

    @Then("the response should show active status as {word}")
    public void theResponseShouldShowActiveStatusAs(String expectedStatus) throws Exception {
        assertNotNull(response.getBody(), "Response body should not be null");
        Map<String, Object> actual = objectMapper.readValue(response.getBody(), Map.class);
        assertEquals(Boolean.parseBoolean(expectedStatus), actual.get("active"));
    }
}