package com.cuckesalad.jsonformatter.models;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class JvmRule {

  private String id;
  private String name;
  private String description;
  private long line;
  private URI uri;
  private Set<JvmTestCase> testCases = new LinkedHashSet<>();
  private String keyword;
  private List<String> tags = new ArrayList<>();

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

  public URI getUri() {
    return uri;
  }

  public void setUri(URI uri) {
    this.uri = uri;
  }

  public Set<JvmTestCase> getTestCases() {
    return testCases;
  }

  public Optional<JvmTestCase> getTestCase(UUID uuid) {
    return testCases.stream().filter(testcase -> testcase.getId().equals(uuid)).findFirst();
  }

  public void setTestCases(Set<JvmTestCase> testCases) {
    this.testCases = testCases;
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public long getLine() {
    return line;
  }

  public void setLine(long line) {
    this.line = line;
  }
}
