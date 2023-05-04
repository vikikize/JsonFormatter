#Simple Feature with no background and rule and example
@Feature01Tag
Feature: Feature 1
  description for feature 1

  #Scenario with tag and error with step def
  @Sceanorio01Tag
  Scenario: Scenario 1
     description for scenario 1

    And the scenario passes
    Then step with args 1 and 2 and a
    And the scenario passes

  #Scenario without tag
  Scenario: Scenario 2
    description for scenario 2

    And the scenario passes
    Then step with args 1 and 2 and 3
