package com.cuckesalad.jsonformatter.models;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class JvmTestStep {

  private UUID id;
  private String codeLocation;
  private Date startTime;
  private Date endTime;
  private String keyword;
  private String name;
  private long line;
  private final Set<JvmHook> beforeSteps = new LinkedHashSet<>();
  private final Set<JvmHook> afterSteps = new LinkedHashSet<>();
  private Set<JvmAttachment> attachments = new LinkedHashSet<>();

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getCodeLocation() {
    return codeLocation;
  }

  public void setCodeLocation(String codeLocation) {
    this.codeLocation = codeLocation;
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

  public Set<JvmAttachment> getAttachments() {
    return attachments;
  }

  public void setAttachments(Set<JvmAttachment> attachments) {
    this.attachments = attachments;
  }

  public void setAttachment(JvmAttachment attachment) {
    this.attachments.add(attachment);
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getLine() {
    return line;
  }

  public void setLine(long line) {
    this.line = line;
  }

  public Set<JvmHook> getBeforeSteps() {
    return beforeSteps;
  }

  public void setBeforeStep(JvmHook beforeStep) {
    this.beforeSteps.add(beforeStep);
  }

  public Set<JvmHook> getAfterSteps() {
    return afterSteps;
  }

  public void setAfterStep(JvmHook afterStep) {
    this.afterSteps.add(afterStep);
  }
}
