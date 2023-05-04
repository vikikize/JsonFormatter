package in.theautomationstack.cucumberjsonformatter.test;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {

  @Given("an example scenario")
  public void anExampleScenario() throws InterruptedException {}

  @When("all step definitions are implemented")
  public void allStepDefinitionsAreImplemented() {}

  @Then("the scenario passes")
  public void theScenarioPasses() {}

  @Then("the scenario fails")
  public void theScenarioFails() {
    int a = 0 / 0;
  }

  @When("step with args {int} and {int} and {int}")
  public void step_with_args_and_and(Integer int1, Integer int2, Integer int3) {}

  @When("step with data table")
  public void step_with_data_table(DataTable dataTable) {}

  @When("step with doc string")
  public void step_with_doc_string(String docString) {}
}
