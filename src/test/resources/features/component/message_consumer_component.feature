Feature: Message Consumer Component Tests
  As a developer
  I want to test message consumer components in isolation
  So that I can ensure message processing works correctly

  Background:
    Given the message consumer is initialized

  Scenario: Process valid JMS message
    Given I have a valid JMS message with trainer data
    When the consumer processes the message
    Then the workload should be extracted correctly
    And the workload service should be called
    And no consumer exception should be thrown

  Scenario: Process message with invalid format
    Given I have a JMS message with invalid format
    When the consumer processes the message
    Then the message should be handled gracefully
    And no consumer exception should be thrown

  Scenario: Process null message
    Given I have a null JMS message
    When the consumer processes the message
    Then the message should be handled gracefully
    And no consumer exception should be thrown

  Scenario: Process message with missing required fields
    Given I have a JMS message missing username field
    When the consumer processes the message
    Then the message should be handled gracefully
    And the workload service should be called
    And an error should be logged