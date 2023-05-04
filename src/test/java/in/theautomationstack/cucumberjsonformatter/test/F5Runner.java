package in.theautomationstack.cucumberjsonformatter.test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    plugin = {
      "pretty",
      "in.theautomationstack.cucumberjsonformatter.JsonFormatter:target/feature5.json",
      "html:target/cucumberReports/html/feature5.html",
      "json:target/cucumberReports/json/feature5.json"
    },
    glue = "in.theautomationstack.cucumberjsonformatter.test",
    features = "src/test/resources/features/feature5.feature")
public class F5Runner extends AbstractTestNGCucumberTests {}
