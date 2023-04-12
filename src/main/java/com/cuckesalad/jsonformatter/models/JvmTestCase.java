package com.cuckesalad.jsonformatter.models;

import java.net.URI;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class JvmTestCase {

  private long line;
  private String name;
  private String description;
  private String keyword;
  private List<String> tags;
  private URI uri;
  private UUID id;
  private JvmBackground background;
  private Set<JvmHook> before = new LinkedHashSet<>();
  private Set<JvmHook> after = new LinkedHashSet<>();
  private Set<JvmTestStep> testSteps = new LinkedHashSet<>();
  private Date startTime;
  private Date endtTime;

  public long getLine() {
    return line;
  }

  public void setLine(long line) {
    this.line = line;
  }

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

  public URI getUri() {
    return uri;
  }

  public void setUri(URI uri) {
    this.uri = uri;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Set<JvmTestStep> getTestSteps() {
    return testSteps;
  }

  public void setTestSteps(Set<JvmTestStep> testSteps) {
    this.testSteps = testSteps;
  }

  public void setTestStep(JvmTestStep testStep) {
    this.testSteps.add(testStep);
  }

  public void removeTestStep(UUID id) {

    Optional<JvmTestStep> jvmTestStep =
        this.testSteps.stream().filter(testStep -> testStep.getId().equals(id)).findFirst();
    this.testSteps.remove(jvmTestStep.get());
  }

  public Optional<JvmTestStep> getTestStep(UUID id) {
    if (id == null) {
      return this.testSteps.stream().filter(testStep -> testStep.getId() == null).findFirst();
    }
    return this.testSteps.stream().filter(testStep -> testStep.getId().equals(id)).findFirst();
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public JvmBackground getBackground() {
    return background;
  }

  public void setBackground(JvmBackground background) {
    this.background = background;
  }

  public Set<JvmHook> getBefore() {
    return before;
  }

  public void setBefore(Set<JvmHook> before) {
    this.before = before;
  }

  public void setBefore(JvmHook jvmhook) {
    this.before.add(jvmhook);
  }

  public Set<JvmHook> getAfter() {
    return after;
  }

  public void setAfter(Set<JvmHook> after) {
    this.after = after;
  }

  public void setAfter(JvmHook after) {
    this.after.add(after);
  }

  public Date getEndtTime() {
    return endtTime;
  }

  public void setEndtTime(Date endtTime) {
    this.endtTime = endtTime;
  }
}
