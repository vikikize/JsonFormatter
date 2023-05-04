package in.theautomationstack.cucumberjsonformatter.test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    plugin = {
      "pretty",
      "in.theautomationstack.cucumberjsonformatter.JsonFormatter:target/feature4.json",
      "html:target/cucumberReports/html/feature4.html",
      "json:target/cucumberReports/json/feature4.json"
    },
    glue = "in.theautomationstack.cucumberjsonformatter.test",
    features = "src/test/resources/features/feature4.feature")
public class F4Runner extends AbstractTestNGCucumberTests {}
