package in.theautomationstack.cucumberjsonformatter.test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    plugin = {
      "pretty",
      "in.theautomationstack.cucumberjsonformatter.JsonFormatter:target/feature1.json",
      "html:target/cucumberReports/html/feature1.html",
      "json:target/cucumberReports/json/feature1.json"
    },
    glue = "in.theautomationstack.cucumberjsonformatter.test",
    features = "src/test/resources/features/feature1.feature")
public class F1Runner extends AbstractTestNGCucumberTests {}
