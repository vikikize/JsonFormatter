package in.theautomationstack.cucumberjsonformatter.test;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import java.io.IOException;

public class Hooks {

  @BeforeAll
  public static void setup() throws IOException {}

  @AfterAll
  public static void tearDown() throws IOException {}

  @Before
  public void before(Scenario scenario) {
    var inputStreamScreenShot = Hooks.class.getResourceAsStream("/screenshot/Screenshot.png");
    try {
      var bytes = inputStreamScreenShot.readAllBytes();
      scenario.attach(bytes, "image/png", "4");
      scenario.attach(bytes, "image/png", "5");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @BeforeStep
  public void beforestep(Scenario scenario) {
    var inputStreamScreenShot = Hooks.class.getResourceAsStream("/screenshot/Screenshot.png");
    try {
      var bytes = inputStreamScreenShot.readAllBytes();
      scenario.attach(bytes, "image/png", "4");
      scenario.attach(bytes, "image/png", "5");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @AfterStep
  public void afterStep(Scenario scenario) {
    var inputStreamScreenShot = Hooks.class.getResourceAsStream("/screenshot/Screenshot.png");
    try {
      var bytes = inputStreamScreenShot.readAllBytes();
      scenario.attach(bytes, "image/png", "4");
      scenario.attach(bytes, "image/png", "5");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @After
  public void after(Scenario scenario) {
    var inputStreamScreenShot = Hooks.class.getResourceAsStream("/screenshot/Screenshot.png");
    try {
      var bytes = inputStreamScreenShot.readAllBytes();
      scenario.attach(bytes, "image/png", "4");
      scenario.attach(bytes, "image/png", "5");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
