#Simple Feature with background and example with rule;
@Feature04Tag
Feature: Feature 4
  description for feature 4

  Background: background 01
    description for background 01

    Given an example scenario
    When all step definitions are implemented

  # Scenario with examples and tagged examples
  @Scenario01Tag
  Scenario: Scenario 02 with examples
    some description of scenario 02

    Then the scenario passes

  Rule: rule 1

  Background: background 02
    description for background 02
  
  	Given an example scenario
  	When all step definitions are implemented
  
  @Scenario01Tag
  Scenario: Scenario 02 with examples
    some description of scenario 02

    Then the scenario passes
