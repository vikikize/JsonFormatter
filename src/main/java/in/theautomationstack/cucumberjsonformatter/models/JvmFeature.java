package in.theautomationstack.cucumberjsonformatter.models;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class JvmFeature {

  private String name;
  private String description;
  private String source;
  private URI uri;
  private String fileName;
  private Set<JvmTestCase> testCases = new LinkedHashSet<>();
  private String keyword;
  private List<String> tags = new ArrayList<>();
  private Date startTime;
  private Date endTime;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public URI getUri() {
    return uri;
  }

  public void setUri(URI uri) {
    this.uri = uri;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public Set<JvmTestCase> getTestCases() {
    return testCases;
  }

  public void setTestCases(Set<JvmTestCase> testCases) {
    this.testCases = testCases;
  }

  public void setTestCase(JvmTestCase testCase) {
    testCases.add(testCase);
  }

  public Optional<JvmTestCase> getTestCase(UUID uuid) {
    return testCases.stream().filter(testcase -> testcase.getId().equals(uuid)).findFirst();
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }
}
