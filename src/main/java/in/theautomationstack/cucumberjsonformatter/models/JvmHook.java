package in.theautomationstack.cucumberjsonformatter.models;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class JvmHook {

  private String type;
  private UUID id;
  private String codeLocation;
  private Date startTime;
  private Date endTime;
  private String keyword;
  private String name;
  private long line;
  private Set<JvmAttachment> attachments = new LinkedHashSet<>();
  private JvmStatus status;

  public JvmHook(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

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

  public Set<JvmAttachment> getAttachments() {
    return attachments;
  }

  public void setAttachments(Set<JvmAttachment> attachments) {
    this.attachments = attachments;
  }

  public void setAttachment(JvmAttachment jvmAttachment) {
    this.attachments.add(jvmAttachment);
  }

  public JvmStatus getStatus() {
    return status;
  }

  public void setStatus(JvmStatus status) {
    this.status = status;
  }
}
