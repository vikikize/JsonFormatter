package com.cuckesalad.jsonformatter.models;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class JvmBackground {

  private String name;
  private String description;
  private String id;
  private Set<JvmTestStep> testSteps = new LinkedHashSet<>();

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

  public Set<JvmTestStep> getTestSteps() {
    return testSteps;
  }

  public Optional<JvmTestStep> getTestStep(UUID id) {
    return testSteps.stream().filter(testStep -> testStep.getId().equals(id)).findFirst();
  }

  public void setTestSteps(Set<JvmTestStep> testSteps) {
    this.testSteps = testSteps;
  }

  public void setTestStep(JvmTestStep testStep) {
    this.testSteps.add(testStep);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
