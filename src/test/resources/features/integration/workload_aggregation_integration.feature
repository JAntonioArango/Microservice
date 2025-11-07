Feature: Workload Aggregation Integration Tests
  As a microservice client
  I want to test workload aggregation functionality
  So that I can verify summary calculations work correctly

  Background:
    Given the workload service is running

  Scenario: Multiple workloads aggregation
    Given a trainer "trainer456" has multiple workload entries:
      | firstName | lastName | active | trainingDate        | trainingDuration |
      | Jane      | Smith    | true   | 2024-01-15T10:00:00 | 60               |
      | Jane      | Smith    | true   | 2024-01-20T14:00:00 | 90               |
      | Jane      | Smith    | true   | 2024-02-05T09:00:00 | 45               |
    When I send a GET request to "/api/workload/v1/summary/trainer456"
    Then the response status should be 200
    And the summary should show total duration of 195

  Scenario: Inactive trainer workload handling
    Given a trainer "trainer789" has workload data with status changes:
      | firstName | lastName | active | trainingDate        | trainingDuration |
      | Bob       | Johnson  | true   | 2024-01-10T10:00:00 | 60               |
      | Bob       | Johnson  | false  | 2024-01-15T14:00:00 | 90               |
    When I send a GET request to "/api/workload/v1/summary/trainer789"
    Then the response status should be 200
    And the response should show active status as true