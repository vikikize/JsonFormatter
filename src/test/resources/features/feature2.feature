#Simple Feature with no background and rule. But with examples and docstring and datatable.
@Feature02Tag
Feature: Feature 2
  description for feature 2

  # Scenario with datatable and doc string
  Scenario: Scenario 1
     some description of scenario 2

    When all step definitions are implemented
    And the scenario passes
    And step with args 1 and 2 and 3
    And step with data table
      | column1 | column2 | column3 |
      |       1 |      11 |     111 |
      |       2 |      22 |     222 |
    And step with doc string
      """
      something
      goes here
      :)
      """

  # Scenario with examples and tagged examples
  @Scenario02Tag
  Scenario Outline: Scenario 02 with examples
    some description of scenario 02

    When all step definitions are implemented
    Then the scenario passes

    Examples: 
      | data |
      |    1 |

    @Example1
    Examples: 
      | data |
      |    1 |

    @Example2
    Examples: 
      | data |
      |    2 |
