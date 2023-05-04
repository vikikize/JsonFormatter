package in.theautomationstack.cucumberjsonformatter.jsontest;

import in.theautomationstack.cucumberjsonformatter.models.JvmFeature;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FeatureTest {

  private JvmFeature[] jvmfeatures;

  public FeatureTest(JvmFeature[] jvmfeatures) {
    this.jvmfeatures = jvmfeatures;
  }

  @Test(testName = "featureCount")
  public void countFeature() {
    Assert.assertEquals(jvmfeatures.length, 3);
  }
}
