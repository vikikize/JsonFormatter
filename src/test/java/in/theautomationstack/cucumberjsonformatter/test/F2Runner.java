package in.theautomationstack.cucumberjsonformatter.test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    plugin = {
      "pretty",
      "in.theautomationstack.cucumberjsonformatter.JsonFormatter:target/feature2.json",
      "html:target/cucumberReports/html/feature2.html",
      "json:target/cucumberReports/json/feature2.json"
    },
    glue = "in.theautomationstack.cucumberjsonformatter.test",
    features = "src/test/resources/features/feature2.feature")
public class F2Runner extends AbstractTestNGCucumberTests {}
