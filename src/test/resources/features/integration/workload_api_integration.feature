Feature: Workload API Integration Tests
  As a microservice client
  I want to interact with the workload API endpoints
  So that I can manage trainer workload data

  Background:
    Given the workload service is running

  Scenario: Save workload successfully
    When I send a POST request to "/api/workload/v1/saveworkload" with:
      | username        | trainer123    |
      | firstName       | John          |
      | lastName        | Doe           |
      | active          | true          |
      | trainingDate    | 2024-01-15T10:00:00 |
      | trainingDuration| 60            |
    Then the response status should be 200

  Scenario: Get workload summary
    Given a trainer "trainer123" has workload data:
      | firstName | lastName | active | trainingDate        | trainingDuration |
      | John      | Doe      | true   | 2024-01-15T10:00:00 | 60               |
    When I send a GET request to "/api/workload/v1/summary/trainer123"
    Then the response status should be 200
    And the response should contain:
      | username      | trainer123 |
      | firstName     | John       |
      | lastName      | Doe        |
      | active        | true       |

  Scenario: Get workload summary for non-existent trainer
    When I send a GET request to "/api/workload/v1/summary/nonexistent"
    Then the response status should be 400

  Scenario: Save workload with invalid data
    When I send a POST request to "/api/workload/v1/saveworkload" with:
      | username        |               |
      | firstName       | John          |
      | lastName        | Doe           |
      | active          | true          |
      | trainingDate    | 2024-01-15T10:00:00 |
      | trainingDuration| 60            |
    Then the response status should be 500