package in.theautomationstack.cucumberjsonformatter.models;

import java.net.URI;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class TestExecution {

  private Date startTime;
  private Date endTime;
  private Set<JvmFeature> features = new LinkedHashSet<>();

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Set<JvmFeature> getFeatures() {
    return features;
  }

  public void setFeatures(Set<JvmFeature> features) {
    this.features = features;
  }

  public void setFeature(JvmFeature feature) {
    this.features.add(feature);
  }

  public Optional<JvmFeature> getFeature(URI uri) {
    return this.features.stream().filter(feature -> feature.getUri().equals(uri)).findFirst();
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }
}
