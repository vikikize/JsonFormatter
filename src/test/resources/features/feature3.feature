#Simple Feature with background and example
@Feature03Tag
Feature: Feature 3
  description for feature 3

  Background: background 01
    description for background 01

    Given an example scenario
    When all step definitions are implemented

  # Scenario with examples and tagged examples
  @Scenario02Tag
  Scenario Outline: Scenario 02 with examples
    some description of scenario 02

    Then the scenario passes

    Examples: 
      | data |
      |    1 |

    @Example1
    Examples: 
      | data |
      |    2 |
