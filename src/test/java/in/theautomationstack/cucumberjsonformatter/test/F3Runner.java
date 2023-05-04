package in.theautomationstack.cucumberjsonformatter.test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    plugin = {
      "pretty",
      "in.theautomationstack.cucumberjsonformatter.JsonFormatter:target/feature3.json",
      "html:target/cucumberReports/html/feature3.html",
      "json:target/cucumberReports/json/feature3.json"
    },
    glue = "in.theautomationstack.cucumberjsonformatter.test",
    features = "src/test/resources/features/feature3.feature")
public class F3Runner extends AbstractTestNGCucumberTests {}
