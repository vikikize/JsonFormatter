package in.theautomationstack.cucumberjsonformatter.jsontest;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import in.theautomationstack.cucumberjsonformatter.models.JvmFeature;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.testng.annotations.BeforeTest;

public class BaseTest {

  private JvmFeature[] jvmfeatures;

  public BaseTest(JvmFeature[] jvmfeatures)
      throws JsonSyntaxException, JsonIOException, FileNotFoundException {
    Gson gson = new Gson();
    if (jvmfeatures.length == 0) {
      jvmfeatures =
          gson.fromJson(
              new FileReader(new File(System.getProperty("user.dir") + "/target/vkd.json")),
              JvmFeature[].class);
    }
    this.jvmfeatures = jvmfeatures;
  }

  @BeforeTest
  public void beforeTest() {}
}
