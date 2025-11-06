Feature: Workload Service Component Tests
  As a developer
  I want to test workload service components in isolation
  So that I can ensure business logic works correctly

  Background:
    Given the workload service is initialized

  Scenario: Save valid workload data
    Given I have valid workload data for trainer "john.trainer"
    When I save the workload through the service
    Then the workload should be saved successfully
    And the service should call the repository

  Scenario: Save workload with invalid username
    Given I have workload data with blank username
    When I save the workload through the service
    Then a validation exception should be thrown
    And the service should call the repository

  Scenario: Get workload summary for existing trainer
    Given trainer "jane.trainer" has existing workload data
    When I request workload summary through the service
    Then I should receive a valid summary
    And the summary should contain correct trainer information

  Scenario: Get workload summary for non-existing trainer
    Given trainer "unknown.trainer" has no workload data
    When I request workload summary through the service
    Then an empty summary should be returned
    And no service exception should be thrown

  Scenario: Delete workload by valid ID
    Given a workload exists with ID 123
    When I delete the workload through the service
    Then the workload should be deleted
    And the repository delete method should be called

  Scenario: Delete workload by invalid ID
    Given no workload exists with ID 999
    When I delete the workload through the service
    Then no service exception should be thrown
    And the repository delete method should still be called